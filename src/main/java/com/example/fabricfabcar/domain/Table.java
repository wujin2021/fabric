package com.example.fabricfabcar.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Table {

    private String formName;
    private String field1;
    private String field2;
    private String field3;
    private String field4;
    private String field5;
    private ElseAttribute[] elseData;

    // 请确保提供默认构造函数和getter/setter方法



}
