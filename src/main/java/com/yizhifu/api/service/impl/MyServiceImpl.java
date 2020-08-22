package com.yizhifu.api.service.impl;

import com.yizhifu.api.dto.MethodDTO;
import com.yizhifu.api.service.MyService;
import com.yizhifu.api.tools.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class MyServiceImpl implements MyService {
    @Autowired
    private RestTemplate restTemplate;

    //这两个常量按照需要在其他地方配置
    private static final String HOST_NAME = "https://merchant.pangu.life";

    private static final String SECRET_KEY = "2Qg4aWA1W3fkaPg322Q7i3vQWXzGdAH40O5NKrO6";

    @Override
    public String submit(MethodDTO methods) {
        methods.setTimestamp(System.currentTimeMillis()/1000);
        Map<String, String> map = new HashMap<>();
        map.put("timestamp", methods.getTimestamp().toString());
        map.put("accesskey", methods.getAccesskey());
        map.put("bankname", methods.getBankname());
        map.put("realname", methods.getRealname());
        map.put("monoey", methods.getMoney());
        map.put("cardnumber", methods.getCardnumber());
        map.put("orderid", methods.getOrderid());
        map.put("callbackurl", methods.getCallbackurl());
        map.put("ext", methods.getExt());
        methods.setSign(getSign(order(map)));
        return restTemplate.postForObject(HOST_NAME + "/api/broker/submit", methods, String.class);
    }

    @Override
    public String queryOrder(MethodDTO methods) {
        methods.setTimestamp(System.currentTimeMillis()/1000);
        Map<String, String> map = new HashMap<>();
        map.put("timestamp", methods.getTimestamp().toString());
        map.put("accesskey", methods.getAccesskey());
        map.put("orderid", methods.getOrderid());
        methods.setSign(getSign(order(map)));
        return restTemplate.postForObject(HOST_NAME + "/api/broker/queryorder", methods, String.class);

    }

    @Override
    public String queryBalance(MethodDTO methods) {
        methods.setTimestamp(System.currentTimeMillis()/1000);
        Map<String, String> map = new HashMap<>();
        map.put("timestamp", methods.getTimestamp().toString());
        map.put("accesskey", methods.getAccesskey());
        methods.setSign(getSign(order(map)));
        return restTemplate.postForObject(HOST_NAME + "/api/broker/querybalance", methods, String.class);

    }

    @Override
    public String callback(MethodDTO methods) {
        Map<String, String> map = new HashMap<>();
        map.put("timestamp", methods.getTimestamp().toString());
        map.put("accesskey", methods.getAccesskey());
        map.put("orderid", methods.getOrderid());
        map.put("status", methods.getStatus());
        //计算签名
        String sign = getSign(order(map));
        //判断签名是否相等
        if(Objects.equals(methods.getSign(), sign)){
            return "200";
        }
        return "400";
    }

    //key按照字典排序
    private static Map<String, String> order(Map<String, String> map) {
        HashMap<String, String> tempMap = new LinkedHashMap<String, String>();
        List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(map.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return (o1.getKey()).compareTo(o2.getKey());
            }
        });
        for (int i = 0; i < infoIds.size(); i++) {
            Map.Entry<String, String> item = infoIds.get(i);
            tempMap.put(item.getKey(), item.getValue());
        }
        return tempMap;
    }

    //计算签名值
    private static String getSign(Map<String, String> orderedMap){
        Set<Map.Entry<String, String>> entries = orderedMap.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        StringBuilder sb = new StringBuilder();
        //按顺序取出value
        while (iterator.hasNext()){
            sb.append(iterator.next().getValue());
        }
        //计算并返回签名值sign
        return MD5Utils.EncoderByMd5(sb.append(SECRET_KEY).toString());
    }
}
