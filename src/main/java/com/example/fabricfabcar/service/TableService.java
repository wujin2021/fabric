package com.example.fabricfabcar.service;

import com.example.fabricfabcar.domain.DynamicTableData;
import com.example.fabricfabcar.domain.TableMetadata;
import com.example.fabricfabcar.domain.TableName;
import com.example.fabricfabcar.mapper.TableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {
    private final TableMapper tableMapper;

    @Autowired
    public TableService(TableMapper tableMapper) {
        this.tableMapper = tableMapper;
    }

    public void createTable(TableMetadata tableMetadata) {
        tableMapper.createTable(tableMetadata.getTableName(), tableMetadata.getColumns());
    }

    public List<TableName> getAllTableNames(String databaseName) {
        return tableMapper.getAllTableNames(databaseName);
    }

    public List<String> getTableColumns(String databaseName, String tableName) {
        return tableMapper.getTableColumns(databaseName, tableName);
    }

}

