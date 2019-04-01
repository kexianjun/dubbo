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
package org.apache.dubbo.demo.consumer;

import org.apache.dubbo.demo.DemoService;
import org.apache.dubbo.demo.PersonTest;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {
    /**
     * In order to make sure multicast registry works, need to specify '-Djava.net.preferIPv4Stack=true' before
     * launch the application
     */
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/dubbo-consumer.xml");
        context.start();
        DemoService demoService = context.getBean("demoService", DemoService.class);
        String hello = demoService.sayHello("world");
        System.out.println("result: " + hello);
        PersonTest<String> personTest = new PersonTest<>();
        personTest.setName("person name");
        personTest.setAge("person age");
        /*Map<String, List<PersonTest<String>>> resultMap = demoService.sayHi("name");
        resultMap.forEach((k, v) -> {
            System.out.println(k);
            v.forEach(person -> {
                System.out.println(person.getAge());
                System.out.println(person.getName());

            });
        });*/
        System.out.println("new result:" + demoService.fastjsonTest1(personTest));
        System.out.println("new result:" + demoService.fastjsonTest1(Stream.of(1,2,3)
                .map(i -> {
                    PersonTest<String> person = new PersonTest<>();
                    person.setAge(i + "age");
                    person.setName(i + "name");
                    return person;
                }).collect(Collectors.toList())));

        /*Map<String, List<PersonTest<String>>> map = new HashMap<>();
        Stream.of(1, 2, 3).forEach(i -> {
            List<PersonTest<String>> personTestList = Stream.of("a", "b", "c").map(c -> {
                PersonTest<String> person = new PersonTest<>();
                person.setName("name:" + c);
                person.setAge("haha");
                return person;
            }).collect(Collectors.toList());
            map.put(i + ":" + "name", personTestList);
        });
        System.out.println("new result:" + demoService.fastjsonTest1(map));*/

    }
}
