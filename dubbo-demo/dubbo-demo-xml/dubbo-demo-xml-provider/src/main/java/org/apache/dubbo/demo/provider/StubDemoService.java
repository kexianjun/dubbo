package org.apache.dubbo.demo.provider;

import org.apache.dubbo.demo.DemoService;

public class StubDemoService implements DemoService {
    private DemoService demoService;

    public StubDemoService(DemoService demoService) {
        this.demoService = demoService;
    }
    @Override
    public String sayHello(String name) {
        System.out.println("stub service begin");
        String hello = demoService.sayHello(name);
        System.out.println("stub service end");
        return hello;
    }
}
