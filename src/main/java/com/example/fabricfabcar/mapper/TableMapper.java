package com.example.fabricfabcar.mapper;
import com.example.fabricfabcar.domain.ColumnMetadata;
import com.example.fabricfabcar.domain.DynamicTableData;
import com.example.fabricfabcar.domain.TableName;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TableMapper {
    @UpdateProvider(type = TableSqlProvider.class, method = "createTableSql")
    void createTable(@Param("tableName") String tableName, @Param("columns") List<ColumnMetadata> columns);

    @Select("SELECT table_name AS name FROM information_schema.tables WHERE table_schema = #{databaseName}")
    List<TableName> getAllTableNames(@Param("databaseName") String databaseName);

    @Select("SELECT column_name FROM information_schema.columns WHERE table_schema = #{databaseName} AND table_name = #{tableName}")
    List<String> getTableColumns(@Param("databaseName") String databaseName, @Param("tableName") String tableName);

}
