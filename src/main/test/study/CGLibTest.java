import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;
import java.lang.reflect.Method;

public class CGLibTest {


    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MethodAnnotation {
        String key();
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MethodAnnotation1 {
        String key();
    }

    @MethodAnnotation(key = "xxx")
    @MethodAnnotation1(key = "yyy")
    public String test(){
        System.out.println("hello");
        return "return";
    }

    public static void main(String[] args) {
        try {
            System.out.println(CGLibTest.class.getMethod("test").getAnnotatedReturnType().getType());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CGLibTest.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("before");
                Object res = methodProxy.invokeSuper(o,objects);
                System.out.println("after");
                return res;
            }
        });

        CGLibTest test = (CGLibTest)enhancer.create();
        test.test();
        System.out.println(test.hashCode());
        System.out.println(test.getClass());
        System.out.println(test.toString());
    }
}
