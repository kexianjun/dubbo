package org.apache.dubbo.demo;

import java.io.Serializable;

public class School implements Serializable {
    private String name;
    private Integer rooms;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }
}
