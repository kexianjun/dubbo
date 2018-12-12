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

import org.apache.dubbo.demo.ClassEnum;
import org.apache.dubbo.demo.DemoService;
import org.apache.dubbo.demo.School;
import org.apache.dubbo.demo.Student;
import org.apache.dubbo.rpc.RpcContext;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }

    @Override
    public String routeMethod1() {
        System.out.println("routeMethod1 was called: " + RpcContext.getContext().getLocalAddress());
        return "routeMethod1";
    }

    @Override
    public String routeMethod2() {
        System.out.println("routeMethod2 was called: " + RpcContext.getContext().getLocalAddress());
        return "routeMethod2";
    }

    @Override
    public String sayHello(Integer id) {
        return "integer from sayHello:" + id;
    }

    @Override
    public String sayHello(ClassEnum classEnum) {
        return "enum from sayHello:" + classEnum.getDesc();
    }

    @Override
    public String sayHello(Student student) {
        return "student from sayHello";
    }

    @Override
    public String sayHello(Student student, School school) {
        return student.getName() + "," + school.getName() + " from sayHello";
    }

    @Override
    public String sayHello(Long id) {
        return "long from sayHello";
    }

    @Override
    public String sayHello(Integer id1, Long id2) {
        return id1 + " and " + id2 + " from sayHello";
    }
}
