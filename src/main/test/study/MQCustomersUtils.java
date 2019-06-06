package study;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by yukaiji
 */
public class MQCustomersUtils {

    /**
     * 消费默认DefaultExchange的指定队列信息
     * @param consumerName 消费者连接名称
     * @param queueName 消息队列名称
     */
    public static void consumeDefaultExchangeMessage(String consumerName,String queueName){
        Channel channel = MQProductsUtils.getChannelInstance(consumerName);
        try {
            Consumer consumer = new DefaultConsumer(channel){
                /**
                 * 重写消费处理过程
                 * @param consumerTag 接收到消息时的消费者Tag（随机生成），可以在basicConsume中指定
                 * @param envelope 消息的属性，包含
                 *                 1.deliveryTag 消息发送编号，
                 *                 2.redeliver 重传标志，确认在收到对消息的失败确认后，是否需要重发这条消息，这里的值为false，不需要重发。
                 *                 3.exchange exchange名称，默认为""
                 *                 4.消息发送的路由Key，这里是发送消息时设置的queueName
                 * @param properties basicPublish中的props参数
                 * @param body 消息内容
                 */
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("消费者消费消息:" + new String(body));
                    //System.out.println(consumerTag);
                    //System.out.println(envelope.toString());
                    //System.out.println(properties.toString());
                    // 需要手动确认消息时，通过Channel.basicAck方法，发送确认消息给消息队列
                    // 参数 1.消息的发送编号 2.确认方式，true为确认所有编号小于等于参数1的消息，false只确认当前消息
                    // this.getChannel().basicAck(envelope.getDeliveryTag(), false);
                }
            };
            // 这里与消息队列queueName进行绑定，否则无法从队列中消费信息
            // 参数 1.绑定的队列名称 2. 自动确认标志，true时自动发送确认消息ACK给队列 3.consume对象，执行处理消息逻辑
            channel.basicConsume(queueName, true, consumer);
        } catch (Exception e) {
            System.out.println(consumerName + "消费" + queueName + "队列消息失败");
        }
    }

    /**
     * 消费默认DefaultExchange的指定队列信息
     * @param consumerName 消费者连接名称
     * @param queueName 消息队列名称
     */
    public static void consumeDirectExchangeMessage(String consumerName,String queueName){
        Channel channel = MQProductsUtils.getChannelInstance(consumerName);
        try {
            Consumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("消费者消费" + queueName + "队列消息:" + new String(body));
                }
            };
            channel.basicConsume(queueName, true, consumer);
        } catch (Exception e) {
            System.out.println(consumerName + "消费" + queueName + "队列消息失败");
        }
    }


    public static void main(String[] args) {

        try {
//            consumeDefaultExchangeMessage("测试消费者", "FirstQueue");
//            consumeDefaultExchangeMessage("测试消费者2", "SecondQueue");
            consumeDefaultExchangeMessage("测试消费者3", "DirectQueue");
        } catch (Exception e) {
            System.out.println("发生了异常，" + e);
        }

    }

}
