package org.apache.dubbo.demo;

import java.io.Serializable;

public class PersonTest<T> implements Serializable {
    private String name;
    private T age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getAge() {
        return age;
    }

    public void setAge(T age) {
        this.age = age;
    }
}
