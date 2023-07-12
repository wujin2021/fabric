package com.example.fabricfabcar.mapper;
import com.example.fabricfabcar.domain.Car;
import com.example.fabricfabcar.domain.DynamicTableData;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface DataMapper {
    @Select("select * from cars")
    List<Car> getAll();

    @Select("select `key`,`make`,`model`,`colour`,`owner` from cars where `key` = #{key}")
    public List<Car> selectBykey(@Param("key")String key);

    @Insert({"insert into cars(`key`, `make`, `model`, `colour`, `owner`) values (#{key}, #{make}, #{model}, #{colour}, #{owner})"})
    //@Options(useGeneratedKeys = true, keyProperty = "id")
    // String key, String make, String model, String colour, String owner
    void insertCar(Car car);
    /*public List<Car> insertCar(@Param("key")String key, @Param("make")String make,
                           @Param("model")String model, @Param("colour")String colour,
                           @Param("owner")String owner);*/
    @InsertProvider(type = DynamicTableSQLProvider.class, method = "insert")
    void insertIntoDynamicTable(DynamicTableData data);

    @SelectProvider(type = DynamicQuerySQLProvider.class, method = "dynamicQuery")
    List<Map<String, Object>> dynamicQueryData(@Param("tableName") String tableName, @Param("conditions") Map<String, Object> conditions);
}
