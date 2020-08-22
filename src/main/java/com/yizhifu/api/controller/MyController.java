package com.yizhifu.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.yizhifu.api.dto.MethodDTO;
import com.yizhifu.api.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/yizhifu")
public class MyController {
    @Autowired
    private MyService myService;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/submit")
    public String submit(@RequestBody MethodDTO methods){

        return myService.submit(methods) ;
    }

    @PostMapping("/queryorder")
    public String queryOrder(@RequestBody MethodDTO methods){

        return myService.queryOrder(methods) ;
    }

    @PostMapping("/querybalance")
    public String queryBalance(@RequestBody MethodDTO methods){

        return myService.queryBalance(methods) ;
    }

    @PostMapping("/callback")
    public String callback(@RequestBody JSONObject json){
        //fastjson将json转成java对象时会自动匹配大小写
        MethodDTO methods = json.toJavaObject(MethodDTO.class);
        return myService.callback(methods) ;
    }
}
