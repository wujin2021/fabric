package com.example.fabricfabcar.service;


import com.alibaba.fastjson.JSON;
import com.example.fabricfabcar.coderUtils.EncryptAndDecrypt;
import com.example.fabricfabcar.domain.Car;
import com.example.fabricfabcar.domain.SimpleBlockInfo;
import com.example.fabricfabcar.utils.HashAndStringUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.binary.StringUtils;
import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.protos.common.Ledger;
import org.hyperledger.fabric.sdk.BlockInfo;
import org.hyperledger.fabric.sdk.BlockchainInfo;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.concurrent.TimeoutException;
@Service
@AllArgsConstructor
public class FabcarService {

    final Gateway gateway;
    final Contract contract;
    final Network network;


    public Map<String, Object> queryCarByKey(String key)  {
        Map<String,Object> result = new HashMap<>();
        try {
            byte[] queryResult = contract.evaluateTransaction("queryCar",key);
            Car car = JSON.parseObject(StringUtils.newStringUtf8(queryResult),Car.class);
            car.setKey(key);

            car.setColour(EncryptAndDecrypt.decrypt(car.getColour()));
            car.setMake(EncryptAndDecrypt.decrypt(car.getMake()));
            car.setModel(EncryptAndDecrypt.decrypt(car.getModel()));
            car.setOwner(EncryptAndDecrypt.decrypt(car.getOwner()));

            result.put("payload", car);
            result.put("status","ok");
        } catch (ContractException e) {
            e.printStackTrace();
            result.put("status","error");
        }
        return result;
    }

    public Map<String, Object> queryAllCars()  {
        Map<String,Object> result = new HashMap<>();
        try {
            byte[] queryResult = contract.evaluateTransaction("queryAllCars");
           // List<Car> list = JSON.parseArray(StringUtils.newStringUtf8(queryResult), Car.class);
           // result.put("payload", list);
            result.put("payload",StringUtils.newStringUtf8(queryResult));
            result.put("status","ok");
        } catch (ContractException e) {
            result.put("status","error");
            e.printStackTrace();
        }
        return result;
    }

    public Map<String, Object> createCar(String carnumber, String make, String model,String colour, String owner)  {
        Map<String,Object> result = new HashMap<>();
      //String carnumber1 = EncryptAndDecrypt.encode(carnumber);
        String make1 = EncryptAndDecrypt.encrypt(make);
        String model1 = EncryptAndDecrypt.encrypt(model);
        String colour1 = EncryptAndDecrypt.encrypt(colour);
        String owner1 = EncryptAndDecrypt.encrypt(owner);
        try {
            byte[] transaction = contract.createTransaction("createCar")
                    .setEndorsingPeers(network.getChannel().getPeers(EnumSet.of(Peer.PeerRole.ENDORSING_PEER)))
                    .submit(carnumber,make1,model1,colour1,owner1);
            result.put("payload", "添加成功");
            result.put("status", "ok");
        } catch (ContractException e) {
            e.printStackTrace();
            result.put("status","error");
        } catch (InterruptedException e) {
            e.printStackTrace();
            result.put("status","error");
        } catch (TimeoutException e) {
            e.printStackTrace();
            result.put("status","error");
        }
        return result;
    }

    public Map<String,Object> updateCarOwner(String carnumber,String newowner)  {
        Map<String,Object> result = new HashMap<>();
        try {
            byte[] transaction = contract.submitTransaction("changeCarOwner", carnumber, newowner);
           // result.put("payload", StringUtils.newStringUtf8(contract.evaluateTransaction("queryCar",carnumber)));
            result.put("status", "ok");
        } catch (ContractException e) {
            e.printStackTrace();
            result.put("status","error");
        } catch (TimeoutException e) {
            e.printStackTrace();
            result.put("status","error");
        } catch (InterruptedException e) {
            e.printStackTrace();
            result.put("status","error");
        }
        return result;
    }

    public Ledger.BlockchainInfo getBlcokchainInfo(){
        BlockchainInfo  blockchainInfo;
        try {
           blockchainInfo = network.getChannel().queryBlockchainInfo();

        }catch (InvalidArgumentException | ProposalException e) {
            throw new RuntimeException(e);
        }
        return blockchainInfo.getBlockchainInfo();
    }

    public BlockInfo getBlcokchainInfoByHash(byte[] str){
        BlockInfo blockInfo;
        try {
            blockInfo =  network.getChannel().queryBlockByHash(str);
        } catch (InvalidArgumentException | ProposalException e) {
            throw new RuntimeException(e);
        }
        return blockInfo;
    }

    public BlockInfo getBlcokchainInfoByNumber(long number){
        BlockInfo blockInfo;
        try {
            blockInfo =  network.getChannel().queryBlockByNumber(number);
        } catch (InvalidArgumentException | ProposalException e) {
            throw new RuntimeException(e);
        }
        return blockInfo;
    }

    public List<SimpleBlockInfo> getBlcokchainInfosByEndNumber(long number){
        List<SimpleBlockInfo> blockInfos = new ArrayList<>();
        while(number>=0){
          try {
            BlockInfo blockInfo;
            blockInfo =  network.getChannel().queryBlockByNumber(number);
            SimpleBlockInfo simpleBlockInfo = new SimpleBlockInfo(blockInfo.getBlockNumber()+1,blockInfo.getPreviousHash(),blockInfo.getDataHash());
            blockInfos.add(simpleBlockInfo);
            number--;
          } catch (InvalidArgumentException | ProposalException e) {
            throw new RuntimeException(e);
          }
        }
        return blockInfos;
    }


}
