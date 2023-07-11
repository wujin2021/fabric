package com.example.fabricfabcar.domain;

public class ElseData {
    private String key;
    private String value;

    // 请确保提供默认构造函数和getter/setter方法

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ElseData{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}

