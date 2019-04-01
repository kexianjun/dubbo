/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.demo.provider;

import org.apache.dubbo.demo.DemoService;
import org.apache.dubbo.demo.PersonTest;
import org.apache.dubbo.rpc.RpcContext;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DemoServiceImpl implements DemoService {
    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Override
    public String sayHello(String name) {
        logger.info("Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }

    @Override
    public Map<String, List<PersonTest<String>>> sayHi(String name) {
        Map<String, List<PersonTest<String>>> map = new HashMap<>();
        Stream.of(1, 2, 3).forEach(i -> {
            List<PersonTest<String>> personTestList = Stream.of("a", "b", "c").map(c -> {
                PersonTest<String> personTest = new PersonTest<>();
                personTest.setName("name:" + c);
                personTest.setAge("haha");
                return personTest;
            }).collect(Collectors.toList());
            map.put(i + ":" + name, personTestList);
        });
        return map;
    }

    @Override
    public String fastjsonTest1(PersonTest<String> personTest) {
        System.out.println(personTest.getName());
        System.out.println(personTest.getAge());
        return "PersonTest<String>";
    }

    @Override
    public String fastjsonTest1(List<PersonTest<String>> list) {
        System.out.println("receive");
        list.forEach(person -> {
            System.out.println(person.getAge());
            System.out.println(person.getName());
        });
        return "List<PersonTest<String>>";
    }

    @Override
    public String fastjsonTest1(Map<String, List<PersonTest<String>>> listMap) {
        listMap.forEach((key, value) -> {
            System.out.println(key);
            value.forEach(person -> {
                System.out.println(person.getAge());
                System.out.println(person.getName());
            });
        });
        return "hello";
    }


}
