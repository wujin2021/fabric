package com.example.fabricfabcar;


import com.example.fabricfabcar.ECCUtils.EncryptAndDecrypt;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.binary.StringUtils;
import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.sdk.BlockchainInfo;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.stereotype.Service;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
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
            result.put("payload", StringUtils.newStringUtf8(queryResult));
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
            result.put("payload", StringUtils.newStringUtf8(queryResult));
            result.put("status","ok");
        } catch (ContractException e) {
            result.put("status","error");
            e.printStackTrace();
        }
        return result;
    }

    public Map<String, Object> createCar(String carnumber, String make, String model,String colour, String owner)  {
        Map<String,Object> result = new HashMap<>();
        String carnumber1 = EncryptAndDecrypt.encode(carnumber);
        String make1 = EncryptAndDecrypt.encode(make);
        String model1 = EncryptAndDecrypt.encode(model);
        String colour1 = EncryptAndDecrypt.encode(colour);
        String owner1 = EncryptAndDecrypt.encode(owner);
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
            result.put("payload", StringUtils.newStringUtf8(contract.evaluateTransaction("queryCar",carnumber)));
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

    public String getChain(){
        BlockchainInfo  blockchainInfo;
        try {
           blockchainInfo = network.getChannel().queryBlockchainInfo();

        } catch (ProposalException e) {
            throw new RuntimeException(e);
        } catch (InvalidArgumentException e) {
            throw new RuntimeException(e);
        }
        return blockchainInfo.getBlockchainInfo().toString();
    }

}
