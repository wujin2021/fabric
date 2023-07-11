package com.example.fabricfabcar.mapper;

import com.example.fabricfabcar.domain.ColumnMetadata;

import java.util.List;
import java.util.Map;

public class TableSqlProvider {
    public String createTableSql(Map<String, Object> params) {
        String tableName = (String) params.get("tableName");
        List<ColumnMetadata> columns = (List<ColumnMetadata>) params.get("columns");

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE ").append(tableName).append(" (");

        for (ColumnMetadata column : columns) {
            sqlBuilder.append(column.getColumnName()).append(" ").append(column.getColumnType()).append(", ");
        }

        sqlBuilder.setLength(sqlBuilder.length() - 2); // 去除最后的逗号和空格
        sqlBuilder.append(")");

        return sqlBuilder.toString();
    }
}

