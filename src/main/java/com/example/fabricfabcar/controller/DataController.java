package com.example.fabricfabcar.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.fabricfabcar.coderUtils.EncryptAndDecrypt;
import com.example.fabricfabcar.domain.*;
import com.example.fabricfabcar.mapper.CarDAO;
import com.example.fabricfabcar.service.DataService;
import com.example.fabricfabcar.service.TableService;
import com.example.fabricfabcar.utils.HashAndStringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.sdk.BlockInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Controller
@RequestMapping("/data")
@Slf4j
@AllArgsConstructor
public class DataController {
    final Gateway gateway;
    final Contract contract;
    final DataService dataService;
    final TableService tableService;
    final CarDAO carDAO;


//    /*根据key查询对应的car*/
//    @GetMapping("/queryBykey")
//    public String queryCarByKey(@RequestParam("key") String key, Model model)  {
//
//
//        Map<String, Object> result;
//        result = fabcarService.queryCarByKey(key);
//        System.out.println("result="+result);
//        model.addAllAttributes(result);
//
//        return "aloneCar.html";
//    }

    /*根据key查询对应的car*/
//    @GetMapping("/queryBykey")
//    public String queryCarByKey(@RequestParam("key") String key, Model model) throws NoSuchAlgorithmException, JsonProcessingException {
//
//        Map<String, Object> result = new HashMap<>();
//        List<Car> car = dataService.selectCarinfoByKey(key);
//        System.out.println("carinfo="+car);
//        result.put("carinfo", car);
//        String hash1 = dataService.queryCarByKey(key);
//        System.out.println("hash1="+hash1);
//        result.put("hash1", hash1);
//        String hash2 = dataService.selectCarHashByKey(key);
//        result.put("hash2", hash2);
//        System.out.println("result="+result);
//        /*ObjectMapper mapper = new ObjectMapper();
//        JsonNode tree1 = mapper.readTree(hash1);
//        JsonNode tree2 = mapper.readTree(hash2);*/
//        if(hash1.equals(hash2)){
//            result.put("result1","TRUE");
//        }else {
//            result.put("result1","FALSE");
//        }
//        model.addAllAttributes(result);
//        return "aloneCar";
//    }

    @ResponseBody
    @PostMapping("/dynamic-query")
    public List<Map<String, Object>> dynamicQuery(@RequestBody DynamicQueryRequest request) {
        String tableName = request.getTableName();
        Map<String, Object> conditions = request.getConditions();
        System.out.println(conditions);
        Iterator<Map.Entry<String, Object>> iterator = conditions.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String oldKey = entry.getKey();
            Object value = entry.getValue();
            // 修改键
            String newKey = tableService.getTableColumns("fabcars",tableName).get(0);
            entry.setValue(null); // 将值设为null，即删除原有键值对
            iterator.remove(); // 从Map中删除原有键值对
            conditions.put(newKey, value); // 添加新的键值对
        }
//        for (Map.Entry<String, Object> entry : conditions.entrySet()) {
//            String columnName = entry.getKey();
//            Object value = entry.getValue();
//            System.out.println(columnName);
//            System.out.println(value);
//            //System.out.println(conditions.columnName);
//        }
        return dataService.dynamicQuery(tableName, conditions);
    }

    @ResponseBody
    @GetMapping("/validateBykey")
    public String queryCarByKey(@RequestBody DynamicQueryRequest request) throws NoSuchAlgorithmException {
        String tableName = request.getTableName();
        Map<String, Object> conditions = request.getConditions();
        Iterator<Map.Entry<String, Object>> iterator = conditions.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String oldKey = entry.getKey();
            Object value = entry.getValue();
            // 修改键
            String newKey = tableService.getTableColumns("fabcars",tableName).get(0);
            entry.setValue(null); // 将值设为null，即删除原有键值对
            iterator.remove(); // 从Map中删除原有键值对
            conditions.put(newKey, value); // 添加新的键值对
        }
        Collection<Object> values = conditions.values();
        Iterator<Object> iterator1 = values.iterator();
        Object value = null;
        if (iterator1.hasNext()) {
            Object value1 = iterator1.next();
            System.out.println(value1);
            value = value1;
        }
        List<Map<String, Object>> list = dataService.dynamicQuery(tableName, conditions);
        Map<String, Object> result = new HashMap<>();
        System.out.println("carinfo="+list);
        result.put("carinfo", list);
        String hash1 = dataService.queryCarByKey(value.toString());
        System.out.println("hash1="+hash1);
        result.put("hash1", hash1);
        String hash2 = dataService.selectDataHashByKey(tableName, conditions);
        result.put("hash2", hash2);
        System.out.println("result="+result);
        /*ObjectMapper mapper = new ObjectMapper();
        JsonNode tree1 = mapper.readTree(hash1);
        JsonNode tree2 = mapper.readTree(hash2);*/
        if(hash1.equals(hash2)){
            result.put("result1","TRUE");
        }else {
            result.put("result1","FALSE");
        }
        return result.toString();
    }

    /*查询所有的car*/
    @GetMapping("/all")
    public String queryAllCar(Model model)  {

        Map<String, Object> result ;
        result = dataService.queryAllCars();
        System.out.println("result="+result);
        model.addAllAttributes(result);

        return "allCar.html";
    }

//    /*新增一个car的资产*/
//    @PostMapping("/add")
//    public String createCar(@RequestParam("carnumber")String carnumber,@RequestParam("make")String make,
//                                        @RequestParam("model")String model,@RequestParam("colour")String colour,
//                                        @RequestParam("owner")String owner,Model model1){
//
//        Map<String, Object> result;
//        result = fabcarService.createCar(carnumber,make,model,colour,owner);
//        System.out.println("result="+result);
//        model1.addAllAttributes(result);
//
//        return "create.html";
//    }

    /*新增一个car的资产*/
//    @PostMapping("/add")
//    public String createCar(@RequestParam("carnumber")String carnumber,@RequestParam("make")String make,
//                            @RequestParam("model")String model,@RequestParam("colour")String colour,
//                            @RequestParam("owner")String owner,Model model1) throws NoSuchAlgorithmException {
//        String owner1 = EncryptAndDecrypt.encrypt(owner);
//        System.out.println(owner1);
//        fabcarService.insertCar(carnumber,make,model,colour,owner1);
//        String carhash = fabcarService.hashCar(carnumber,make,model,colour,owner1);
//        Map<String, Object> result;
//        result = fabcarService.createCar(carnumber, carhash);
//        System.out.println("result="+result);
//        model1.addAllAttributes(result);
//        return "create";
//    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String creatData(@RequestBody JSONObject jsonParam) throws JsonProcessingException, NoSuchAlgorithmException {
        // 直接将json信息打印出来
        System.out.println(jsonParam.toJSONString());
        String jsonString = jsonParam.toJSONString();
        // 创建Gson对象
        Gson gson = new Gson();
        // 将JSON字符串转换为Java对象
        Data1 data1 = gson.fromJson(jsonString, Data1.class);
        System.out.println(data1);
        String formName = data1.getFormName();
        List<String> tableColumns = tableService.getTableColumns("fabcars", formName);
        List<String> fields = new ArrayList();
        String field1 = data1.getField1();
        String field2 = data1.getField2();
        String field3 = data1.getField3();
        String field4 = data1.getField4();
        String field5 = data1.getField5();
        String field5_ = EncryptAndDecrypt.encrypt(field5);
        data1.setField5(field5_);
        fields.add(field1);
        fields.add(field2);
        fields.add(field3);
        fields.add(field4);
        fields.add(field5);
        ElseData[] elseData = data1.getElseData();
        for (int i = 0; i < elseData.length; i++) {
            String otherfield = elseData[i].getValue();
            System.out.println(otherfield);
            fields.add(otherfield);
        }
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < tableColumns.size(); i++) {
            String key = tableColumns.get(i);
            String value = fields.get(i);
            map.put(key, value);
        }
        DynamicTableData dynamicTableData = new DynamicTableData();
        dynamicTableData.setTableName(formName);
        dynamicTableData.setColumnValues(map);
        System.out.println(fields);
        dataService.insertIntoDynamicTable(dynamicTableData);
        String carhash = dataService.hashData(fields);
        Map<String, Object> result1;
        result1 = dataService.createCar(field1, carhash);
        System.out.println("result="+result1);
        //model1.addAllAttributes(result);
        // 将获取的json数据封装一层，然后在给返回
        JSONObject result = new JSONObject();
        result.put("code", "200");
        result.put("msg", "数据添加成功");
        //result.put("data", jsonParam);
        return result.toJSONString();
    }


    /*将一个car资产转移到另一个owner上*/
    @PostMapping("/update")
    public String updateCarOwner(@RequestParam("carnumber")String carnumber,@RequestParam("newowner")String newowner,Model model)  {
        Map<String, Object> result;
        result = dataService.updateCarOwner(carnumber,newowner);
        System.out.println("result="+result);
        model.addAllAttributes(result);

        return "transfer.html";
    }

    @GetMapping("/blocks")

    public String block(Model model)  {
       long height = dataService.getBlcokchainInfo().getHeight();
       System.out.println("height="+height);
       List<SimpleBlockInfo> list = dataService.getBlcokchainInfosByEndNumber(height-1);
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
       BlockInfo blockInfo = dataService.getBlcokchainInfoByHash(HashAndStringUtil.stringToHash(hash));
       SimpleBlockInfo simpleBlockInfo = new SimpleBlockInfo(blockInfo.getBlockNumber(),blockInfo.getPreviousHash(),blockInfo.getDataHash());
       model.addAttribute(simpleBlockInfo);
       return simpleBlockInfo.toString();
    }

    @GetMapping("/queryBlockByNumber")
    @ResponseBody
    public String queryBlockByNumber(@RequestParam("num")String num,Model model)  {
        BlockInfo blockInfo = dataService.getBlcokchainInfoByNumber(Long.valueOf(num));
        SimpleBlockInfo simpleBlockInfo = new SimpleBlockInfo(blockInfo.getBlockNumber(),blockInfo.getPreviousHash(),blockInfo.getDataHash());
        model.addAttribute(simpleBlockInfo);
        return simpleBlockInfo.toString();
    }

    @GetMapping("/block")
    @ResponseBody
    public String queryBlockByHash(@RequestParam("num")String num)  {
        BlockInfo blockInfo = dataService.getBlcokchainInfoByNumber(Long.valueOf(num));

        return blockInfo.getBlock().toString();
    }
    @GetMapping("/addCar")
    @ResponseBody
    public String addCar(@RequestParam("carnumber")String carnumber,@RequestParam("make")String make,
                         @RequestParam("model")String model,@RequestParam("colour")String colour,
                         @RequestParam("owner")String owner){
        //Car car = new Car(carnumber,make,model,colour,owner);
        Car car = new Car();
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
