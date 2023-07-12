package com.example.fabricfabcar.mapper;

import com.example.fabricfabcar.domain.DynamicTableData;

import java.util.Map;

public class DynamicTableSQLProvider {
    public static String insert(DynamicTableData data) {
        String tableName = data.getTableName();
        Map<String, Object> columnValues = data.getColumnValues();

        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (Map.Entry<String, Object> entry : columnValues.entrySet()) {
            String columnName = entry.getKey();
            Object value = entry.getValue();

            columns.append(columnName).append(",");
            values.append("#{columnValues.").append(columnName).append("},");
        }

        String sql = "INSERT INTO " + tableName + " (" +
                columns.substring(0, columns.length() - 1) +
                ") VALUES (" +
                values.substring(0, values.length() - 1) +
                ")";

        return sql;
    }
}
