package com.yizhifu.api.dto;

import lombok.Data;

import java.io.Serializable;

@Data
//数据传输对象，使用lombok注解时，要确保IDEA上面安装了lombok插件
public class MethodDTO implements Serializable {

    //序列化
    private static final long serialVersionUID = 1L;

    private Long timestamp;

    private String accesskey;

    private String bankname;

    private String realname;

    private String money;

    private String cardnumber;

    private String orderid;

    private String callbackurl;

    private String ext;

    private String sign;

    private String status;
}
