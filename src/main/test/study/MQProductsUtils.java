package study;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yukaiji on 2019/5/30.
 */
public class MQProductsUtils {

    private static ConnectionFactory connectionFactory = null;
    private static Connection connection = null;

    /***
     * 设置MQ连接信息
     * @return MQ连接
     */
    private static ConnectionFactory getConnectionFactory() {
        // 为防止重复采用一个connectionFactory
        if (connectionFactory != null){
            return connectionFactory;
        }
        connectionFactory = new ConnectionFactory();
        // 配置连接信息
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        // 网络异常自动连接恢复
        connectionFactory.setAutomaticRecoveryEnabled(true);
        // 每10秒尝试重试连接一次
        connectionFactory.setNetworkRecoveryInterval(10000);
        Map<String, Object> connectionFactoryPropertiesMap = new HashMap<>();

        // 设置MQ属性信息
        connectionFactoryPropertiesMap.put("user", "yukaiji");
        connectionFactoryPropertiesMap.put("description", "My test MQ");
        connectionFactoryPropertiesMap.put("emailAddress", "yukaiji@hotmail.com");
        connectionFactory.setClientProperties(connectionFactoryPropertiesMap);

        return connectionFactory;
    }

    /***
     * 获取生产者Channel
     * @param connectionDescription 生产者名称
     * @return 消息通道
     */
    public static Channel getChannelInstance(String connectionDescription){
        try {
            ConnectionFactory connectionFactory = getConnectionFactory();
            // 创建一个连接,这里采用1个connection
            if (connection == null) {
                connection = connectionFactory.newConnection(connectionDescription);
            }
            // 创建一个channel通道
            return connection.createChannel();
        } catch (Exception e) {
            throw new RuntimeException("获取Channel连接失败");
        }
    }

    /**
     * 设置默认的生产Default Exchange
     * @param connectionDescription 生产者连接名称
     * @param queueName 消息队列名称
     * @return channel连接
     */
    private static Channel getDefaultExchangeChannel(String connectionDescription, String queueName){
        Channel channel = getChannelInstance(connectionDescription);
        try {
            // 参数，
            // 1.队列名称
            // 2.是否持久化
            // 3.是否只适用于当前TCP连接
            // 4.队列不使用时是否自动删除
            // 5.定义了队列的一些参数信息，主要用于Headers Exchange进行消息匹配
            channel.queueDeclare(queueName,true, false, false, null);
        } catch (Exception e) {
            System.out.println(e);
        }
        return channel;
    }

    /**
     * 设置默认的生产Direct Exchange
     * @param connectionDescription 生产者连接名称
     * @param queueName 消息队列名称
     * @return channel连接
     */
    private static Channel getDirectExchangeChannel(String connectionDescription, String queueName){
        Channel channel = getChannelInstance(connectionDescription);
        try {
            // 参数1、Exchange名称，2、Exchange类型N
            channel.exchangeDeclare("directExchange", "direct");
            channel.queueDeclare(queueName, true, false, false, null);
            // 参数1、消息队列名称，2、Exchange名称 3、路由匹配规则RoutingKey
            channel.queueBind(queueName, "directExchange", "directRoutingKey");
        } catch (Exception e) {
            System.out.println(e);
        }
        return channel;
    }


    /**
     * 向DefaultExchange queue发送消息
     * @param connectionDesc 生产者名称
     * @param queueName 消息队列名称
     * @param message 消息内容
     */
    private static void pushMessageToDefaultExchange(String connectionDesc,String queueName, String message){
        try {
            Channel channel = getDefaultExchangeChannel(connectionDesc, queueName);
            // 参数
            // 1.Exchange名称，如果没有指定，则使用Default Exchange
            // 2.routingKey是消息的路由Key，是用于Exchange将消息路由到指定的消息队列时使用
            // 3.props是消息包含的属性信息。RabbitMQ的消息属性和消息体是分开的
            // 4.body是RabbitMQ消息体
            channel.basicPublish("", queueName, null, message.getBytes());
            System.out.println("生产者生产消息给队列 "+ queueName + "，内容为:" + message);
        } catch (Exception e) {
            System.out.println("消息发送到" + queueName + "失败:" + message);
        }
    }

    /**
     * 向DirectExchange queue发送消息
     * @param connectionDesc 生产者名称
     * @param queueName 消息队列名称
     * @param message 消息内容
     */
    private static void pushMessageToDirectExchange(String connectionDesc,String queueName, String message){
        try {
            Channel channel = getDirectExchangeChannel(connectionDesc, queueName);
            // 参数
            // 1.Exchange名称，如果没有指定，则使用Default Exchange
            // 2.routingKey是消息的路由Key，是用于Exchange将消息路由到指定的消息队列时使用
            // 3.props是消息包含的属性信息。RabbitMQ的消息属性和消息体是分开的
            // 4.body是RabbitMQ消息体
            channel.basicPublish("directExchange", "directRoutingKey", null, message.getBytes());
            System.out.println("生产者生产消息给队列 "+ queueName + "，内容为:" + message);
        } catch (Exception e) {
            System.out.println("消息发送到" + queueName + "失败:" + message);
        }
    }



    public static void main(String[] args) {
        // 使用默认Exchange，发送一条消息到队列当中
//        pushMessageToDefaultExchange("测试生产者" ,"FirstQueue", "Hello World");
//        pushMessageToDefaultExchange("测试生产者2" ,"SecondQueue", "Hello World 2");
        pushMessageToDirectExchange("测试DirectExchange", "DirectQueue", "My Queue is Direct");

    }


}
