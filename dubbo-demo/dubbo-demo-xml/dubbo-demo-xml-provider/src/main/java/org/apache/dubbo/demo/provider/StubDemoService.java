package org.apache.dubbo.demo.provider;

import org.apache.dubbo.demo.DemoService;
import org.apache.dubbo.demo.PersonTest;

import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, List<PersonTest<String>>> sayHi(String name) {
        return null;
    }

    @Override
    public String fastjsonTest1(PersonTest<String> personTest) {
        return null;
    }

    @Override
    public String fastjsonTest1(List<PersonTest<String>> list) {
        return null;
    }

    @Override
    public String fastjsonTest1(Map<String, List<PersonTest<String>>> listMap) {
        return null;
    }


}
