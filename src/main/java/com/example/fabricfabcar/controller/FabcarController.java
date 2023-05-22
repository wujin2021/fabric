package com.example.fabricfabcar.controller;

import com.example.fabricfabcar.domain.Car;
import com.example.fabricfabcar.domain.SimpleBlockInfo;
import com.example.fabricfabcar.mapper.CarDAO;
import com.example.fabricfabcar.service.FabcarService;
import com.example.fabricfabcar.utils.HashAndStringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.sdk.BlockInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Controller
@RequestMapping("/car")
@Slf4j
@AllArgsConstructor
public class FabcarController {
    final Gateway gateway;
    final Contract contract;
    final FabcarService fabcarService;
    final CarDAO carDAO;


    /*根据key查询对应的car*/
    @GetMapping("/queryBykey")
    public String queryCarByKey(@RequestParam("key") String key, Model model)  {


        Map<String, Object> result;
        result = fabcarService.queryCarByKey(key);
        System.out.println("result="+result);
        model.addAllAttributes(result);

        return "aloneCar.html";
    }

    /*查询所有的car*/
    @GetMapping("/all")
    public String queryAllCar(Model model)  {

        Map<String, Object> result ;
        result = fabcarService.queryAllCars();
        System.out.println("result="+result);
        model.addAllAttributes(result);

        return "allCar.html";
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

        return "create.html";
    }

    /*将一个car资产转移到另一个owner上*/
    @PostMapping("/update")
    public String updateCarOwner(@RequestParam("carnumber")String carnumber,@RequestParam("newowner")String newowner,Model model)  {
        Map<String, Object> result;
        result = fabcarService.updateCarOwner(carnumber,newowner);
        System.out.println("result="+result);
        model.addAllAttributes(result);

        return "transfer.html";
    }

    @GetMapping("/blocks")

    public String block(Model model)  {
       long height = fabcarService.getBlcokchainInfo().getHeight();
       System.out.println("height="+height);
       List<SimpleBlockInfo> list = fabcarService.getBlcokchainInfosByEndNumber(height-1);
       for (SimpleBlockInfo blockInfo : list) {
           System.out.println(blockInfo.toString());
       }
       Map<String, Object> result = new ConcurrentHashMap<>();
       result.put("blocks",list);
       result.put("height",height);

       model.addAllAttributes(result);
       return "block.html";
    }

    @GetMapping("/queryBlockByHash")
    @ResponseBody
    public String queryBlockByHash(@RequestParam("hash")String hash,Model model)  {
       BlockInfo blockInfo = fabcarService.getBlcokchainInfoByHash(HashAndStringUtil.stringToHash(hash));
       SimpleBlockInfo simpleBlockInfo = new SimpleBlockInfo(blockInfo.getBlockNumber(),blockInfo.getPreviousHash(),blockInfo.getDataHash());
       model.addAttribute(simpleBlockInfo);
       return simpleBlockInfo.toString();
    }

    @GetMapping("/queryBlockByNumber")
    @ResponseBody
    public String queryBlockByNumber(@RequestParam("num")String num,Model model)  {
        BlockInfo blockInfo = fabcarService.getBlcokchainInfoByNumber(Long.valueOf(num));
        SimpleBlockInfo simpleBlockInfo = new SimpleBlockInfo(blockInfo.getBlockNumber(),blockInfo.getPreviousHash(),blockInfo.getDataHash());
        model.addAttribute(simpleBlockInfo);
        return simpleBlockInfo.toString();
    }

    @GetMapping("/block")
    @ResponseBody
    public String queryBlockByHash(@RequestParam("num")String num)  {
        BlockInfo blockInfo = fabcarService.getBlcokchainInfoByNumber(Long.valueOf(num));

        return blockInfo.getBlock().toString();
    }
    @GetMapping("/addCar")
    @ResponseBody
    public String addCar(@RequestParam("carnumber")String carnumber,@RequestParam("make")String make,
                         @RequestParam("model")String model,@RequestParam("colour")String colour,
                         @RequestParam("owner")String owner){
        Car car = new Car(carnumber,make,model,colour,owner);
        Map<String,Object> map = new ConcurrentHashMap<>();
        try{
        int result = carDAO.insert(car);
        if (result > 0) {
            map.put("result","success");
        } else {
            map.put("result","failure");
        }
        }catch (Exception e){
            map.put("result","failure");
        }

        return map.toString();
    }

    @GetMapping("/queryCar")
    @ResponseBody
    public String queryCar(@RequestParam("key")String key){

        Car car  = carDAO.getCar(key);

       return car.toString();

    }
}
