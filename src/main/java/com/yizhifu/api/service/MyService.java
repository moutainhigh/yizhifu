package com.yizhifu.api.service;

import com.yizhifu.api.dto.MethodDTO;

public interface MyService {
    /**
     * 代付单请求
     * @param methods
     * @return
     */
    String submit(MethodDTO methods);

    /**
     * 查询代付订单
     * @param methods
     * @return
     */
    String queryOrder(MethodDTO methods);

    /**
     * 余额查询
     * @param methods
     * @return
     */
    String queryBalance(MethodDTO methods);

    /**
     * 回调接口
     * @param methods
     * @return
     */
    String callback(MethodDTO methods);

}
