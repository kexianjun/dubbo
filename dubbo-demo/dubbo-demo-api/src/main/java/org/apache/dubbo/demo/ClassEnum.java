package org.apache.dubbo.demo;

public enum ClassEnum {
    /**
     * lag
     */
    LAG(1,"big");

    private int code;
    private String desc;
    ClassEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
