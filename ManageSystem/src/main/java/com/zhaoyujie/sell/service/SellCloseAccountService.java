package com.zhaoyujie.sell.service;

import com.alibaba.fastjson.JSONObject;

public interface SellCloseAccountService {

    /**
     * 增加商品销售结账信息
     * 
     * @param req
     * @return
     */
    JSONObject addSellCloseAccount(JSONObject req);

    /**
     * 查询所有的商品销售结账信息
     * 
     * @return
     */
    JSONObject findAllSellCloseAccount();
}
