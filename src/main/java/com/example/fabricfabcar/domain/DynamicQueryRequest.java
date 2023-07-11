package com.example.fabricfabcar.domain;

import lombok.Data;

import java.util.Map;

@Data
public class DynamicQueryRequest {
    private String tableName;
    private Map<String,String> conditions;
}
