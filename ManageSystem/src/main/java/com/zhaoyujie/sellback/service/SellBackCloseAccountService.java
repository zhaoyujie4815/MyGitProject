package com.zhaoyujie.sellback.service;

import com.alibaba.fastjson.JSONObject;

public interface SellBackCloseAccountService {
    
    /**
     * 增加销售退货结账信息
     * 
     * @param req
     * @return
     */
    JSONObject addSellBackCloseAccount(JSONObject req);
    
    
    /**
     * 查询所有的销售退货结账信息
     * 
     * @return
     */
    JSONObject findAllSellBackCloseAccount();
}
