package com.service.proxy.cglibproxy;

import java.lang.reflect.Method;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * @author rtw
 * @since 2019/12/24
 */
public class EnhanceDemo {
    // 创建代理类并运行
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(EnhanceDemo.class);
        enhancer.setCallback(new MethodInterceptorImpl());

        EnhanceDemo demo = (EnhanceDemo)enhancer.create();
        demo.speak();
        System.out.println(demo);
    }

    // 目标类的方法
    public void speak() {
        System.out.println("大家一起来说话吧");
    }

    // 代理方法扩展
    private static class MethodInterceptorImpl implements MethodInterceptor {
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("--------------before--------------");

            Object result = methodProxy.invokeSuper(o, objects);

            System.out.println("--------------after--------------");
            return result;
        }
    }
}
