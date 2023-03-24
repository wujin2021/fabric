package com.example.fabricfabcar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class urlController {

    @GetMapping("index")
    public String getIndex(){
        return "index";
    }

    @GetMapping("query")
    public String getQuery(){
        return "query";
    }

    @GetMapping("create")
    public String getCreate(){
        return "create";
    }
    @GetMapping("transfer")
    public String getTransfer(){
        return "transfer";
    }

//    @GetMapping("block")
//    public String getBlock(){
//        return "block";
//    }
}
