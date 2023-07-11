package com.example.fabricfabcar;

import com.example.fabricfabcar.service.DataService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FabricFabcarApplicationTests {
    final HyperLedgerFabricGatewayJavaConfig config = new HyperLedgerFabricGatewayJavaConfig();

     DataService fabcarService;

    @Test
    void contextLoads() {
    }
    @Test
    void aa(){
        {
            try {
//                Gateway gateway = config.gateway();
//                Network network = config.getNwork(gateway);
//                Contract contract = config.fabcar(network);
//                fabcarService = new FabcarService(gateway,contract,network);
//                System.out.println(fabcarService.getBlcokchainInfo().toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Test
    void test1(){
//        EncryptAndDecrypt encryptAndDecrypt = new EncryptAndDecrypt();
//        String planttext = encryptAndDecrypt.encrypt("123456","1");
//        String text= encryptAndDecrypt.decrypt(planttext,"2");


    }




}
