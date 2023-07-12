package com.example.fabricfabcar.mapper;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class DynamicQuerySQLProvider {
    public static String dynamicQuery(Map<String, Object> params) {
        String tableName = (String) params.get("tableName");
        Map<String, Object> conditions = (Map<String, Object>) params.get("conditions");

        SQL sql = new SQL().SELECT("*").FROM(tableName);

        if (conditions != null && !conditions.isEmpty()) {
            for (Map.Entry<String, Object> entry : conditions.entrySet()) {
                String columnName = entry.getKey();
                Object value = entry.getValue();
                if (value != null) {
                    sql.WHERE(columnName + " = #{conditions." + columnName + "}");
                    //sql.WHERE(columnName + " = 'a'");
                    System.out.println(sql);
                }
            }
        }

        return sql.toString();
    }
}
