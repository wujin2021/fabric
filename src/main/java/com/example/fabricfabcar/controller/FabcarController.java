package com.example.fabricfabcar.controller;

import com.example.fabricfabcar.FabcarService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.gateway.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Map;



@Controller
@RequestMapping("/car")
@Slf4j
@AllArgsConstructor
public class FabcarController {
    final Gateway gateway;
    final Contract contract;
    final FabcarService fabcarService;


    /*根据key查询对应的car*/
    @GetMapping("/queryBykey")
    public String queryCarByKey(@RequestParam("key") String key, Model model)  {


        Map<String, Object> result;
        result = fabcarService.queryCarByKey(key);
        System.out.println("result="+result);
        model.addAllAttributes(result);

        return "aloneCar";
    }

    /*查询所有的car*/
    @GetMapping("/all")
    public String queryAllCar(Model model)  {

        Map<String, Object> result ;
        result = fabcarService.queryAllCars();
        System.out.println("result="+result);
        model.addAllAttributes(result);

        return "allCar";
    }

    /*新增一个car的资产*/
    @PostMapping("/add")
    public String createCar(@RequestParam("carnumber")String carnumber,@RequestParam("make")String make,
                                        @RequestParam("model")String model,@RequestParam("colour")String colour,
                                        @RequestParam("owner")String owner,Model model1){

        Map<String, Object> result;
        result = fabcarService.createCar(carnumber,make,model,colour,owner);
        System.out.println("result="+result);
        model1.addAllAttributes(result);

        return "create";
    }

    /*将一个car资产转移到另一个owner上*/
    @PostMapping("/update")
    public String updateCarOwner(@RequestParam("carnumber")String carnumber,@RequestParam("newowner")String newowner,Model model)  {
        Map<String, Object> result;
        result = fabcarService.updateCarOwner(carnumber,newowner);
        System.out.println("result="+result);
        model.addAllAttributes(result);

        return "transfer";
    }


}
