package com.example.fabricfabcar.domain;

import lombok.Data;

import java.util.Map;

@Data
public class DynamicTableData {
    private String tableName;
    private Map<String, String> columnValues;

    // 构造函数、getter和setter方法
    // ...
}
