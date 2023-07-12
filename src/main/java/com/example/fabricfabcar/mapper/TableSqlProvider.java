package com.example.fabricfabcar.mapper;

import com.example.fabricfabcar.domain.ColumnMetadata;

import java.util.List;
import java.util.Map;

public class TableSqlProvider {
    public String createTableSql(Map<String, Object> params) {
        String tableName = (String) params.get("tableName");
        List<ColumnMetadata> columns = (List<ColumnMetadata>) params.get("columns");

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append(" (");

        for (ColumnMetadata column : columns) {
            sqlBuilder.append(column.getColumnName()).append(" ").append(column.getColumnType()).append(", ");
        }

        ColumnMetadata columnMetadata = columns.get(0);
        //System.out.println(columnMetadata);
        //System.out.println(columnMetadata.getColumnName());
        sqlBuilder.append("PRIMARY KEY(").append(columnMetadata.getColumnName()).append(")");

        //sqlBuilder.setLength(sqlBuilder.length() - 2); // 去除最后的逗号和空格
        sqlBuilder.append(")");

        System.out.println(sqlBuilder.toString());

        return sqlBuilder.toString();
    }
}

