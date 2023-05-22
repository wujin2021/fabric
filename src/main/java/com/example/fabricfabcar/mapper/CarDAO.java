package com.example.fabricfabcar.mapper;

import com.example.fabricfabcar.domain.Car;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CarDAO {
       public int insert(Car car);
       public int update(Car car);
       public Car getCar(String key);
       public int deleteCar(String key);
       public List<Car> getCarList();

}
