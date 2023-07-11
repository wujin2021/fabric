package com.example.fabricfabcar.mapper;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class DynamicQuerySQLProvider {
    public static String dynamicQuery(Map<String, Object> params) {
        String tableName = (String) params.get("tableName");
        Map<String, String> conditions = (Map<String, String>) params.get("conditions");

        SQL sql = new SQL().SELECT("*").FROM(tableName);

        if (conditions != null && !conditions.isEmpty()) {
            for (Map.Entry<String, String> entry : conditions.entrySet()) {
                String columnName = entry.getKey();
                Object value = entry.getValue();
                if (value != null) {
                    sql.WHERE(columnName + " = #{conditions." + columnName + "}");
                }
            }
        }

        return sql.toString();
    }
}
