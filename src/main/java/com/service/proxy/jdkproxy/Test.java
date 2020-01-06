package com.service.proxy.jdkproxy;

/**
 * @author rtw
 * @since 2019/12/24
 */
public class Test {
    public static void main(String[] args) {
        SourceTest sourceTest = new SourceTestImpl();

        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(sourceTest);

        SourceTest proxy = (SourceTest)myInvocationHandler.getProxyInstance();

        proxy.speak();
    }
}
