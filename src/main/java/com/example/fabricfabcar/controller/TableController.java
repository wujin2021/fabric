package com.example.fabricfabcar.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.fabricfabcar.domain.*;
import com.example.fabricfabcar.service.TableService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/table")
@Slf4j
@AllArgsConstructor
public class TableController {
    final TableService tableService;

    @ResponseBody
    @RequestMapping(value = "/addtable", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String creatTable(@RequestBody JSONObject jsonParam) throws JsonProcessingException, NoSuchAlgorithmException {
        // 直接将json信息打印出来
        System.out.println(jsonParam.toJSONString());
        String jsonString = jsonParam.toJSONString();
        // 创建Gson对象
        Gson gson = new Gson();
        // 将JSON字符串转换为Java对象
        Table table = gson.fromJson(jsonString, Table.class);
        System.out.println(table);
        List attributes = new ArrayList();
        String formName = table.getFormName();
        System.out.println(formName);
        //String formName1 = "{\"name\":"+formName+"}";
        List<TableName> tableNames = tableService.getAllTableNames("fabcars");
        //System.out.println(formName1);
        //System.out.println(tableNames);
        boolean isExit = false;
        for (int i = 0; i < tableNames.size(); i++) {
            if(tableNames.get(i).getName().equals(formName)){
                isExit = true;
                break;
            }
        }
        if(!isExit){
            String attribute01 = table.getField1();
            String attribute02 = table.getField2();
            String attribute03 = table.getField3();
            String attribute04 = table.getField4();
            String attribute05 = table.getField5();
            ColumnMetadata columnMetadata1 = new ColumnMetadata();
            columnMetadata1.setColumnName(attribute01);
            columnMetadata1.setColumnType("varchar(255)");
            ColumnMetadata columnMetadata2 = new ColumnMetadata();
            columnMetadata2.setColumnName(attribute02);
            columnMetadata2.setColumnType("varchar(255)");
            ColumnMetadata columnMetadata3 = new ColumnMetadata();
            columnMetadata3.setColumnName(attribute03);
            columnMetadata3.setColumnType("varchar(255)");
            ColumnMetadata columnMetadata4 = new ColumnMetadata();
            columnMetadata4.setColumnName(attribute04);
            columnMetadata4.setColumnType("varchar(255)");
            ColumnMetadata columnMetadata5 = new ColumnMetadata();
            columnMetadata5.setColumnName(attribute05);
            columnMetadata5.setColumnType("varchar(255)");
            attributes.add(columnMetadata1);
            attributes.add(columnMetadata2);
            attributes.add(columnMetadata3);
            attributes.add(columnMetadata4);
            attributes.add(columnMetadata5);

            ElseAttribute[] elseAttribute = table.getElseData();
            for (int i = 0; i < elseAttribute.length; i++) {
                String other = elseAttribute[i].getValue();
                System.out.println(other);
                ColumnMetadata columnMetadata = new ColumnMetadata();
                columnMetadata.setColumnName(other);
                columnMetadata.setColumnType("varchar(255)");
                attributes.add(columnMetadata);
            }
            System.out.println(attributes);
            TableMetadata tableMetadata = new TableMetadata();
            tableMetadata.setTableName(formName);
            tableMetadata.setColumns(attributes);
            tableService.createTable(tableMetadata);
            JSONObject result = new JSONObject();
            result.put("code", "200");
            result.put("msg", "表格创建成功");
            //result.put("data", jsonParam);
            return result.toJSONString();
        }else{
            JSONObject result = new JSONObject();
            result.put("code", "400");
            result.put("msg", "表格"+formName+"已存在，创建失败！");
            //result.put("data", jsonParam);
            return result.toJSONString();
        }

    }

    @ResponseBody
    @GetMapping("/table-names")
    public List<TableName> getAllTableNames() {
        String databaseName = "fabcars";
        return tableService.getAllTableNames(databaseName);
    }

    @ResponseBody
    @GetMapping("/table-columns")
    public List<String> getTableColumns(@RequestParam("tableName")String tableName) {
        String databaseName = "fabcars";
        //return tableName;
        return tableService.getTableColumns(databaseName, tableName);
    }

}
