package com.zhaoyujie.stockup.service;

import com.alibaba.fastjson.JSONObject;

public interface StockUpCloseAccountService {
    
    /**
     * 增加入库结账信息
     * 
     * @param req
     * @return
     */
    JSONObject addStockUpCloseAccount(JSONObject req);
    
    /**
     * 查询入库结账信息
     * 
     * @param req
     * @return
     */
    JSONObject findStockUpCloseAccount(JSONObject req);
    
    /**
     * 查询所有的入库结账信息
     * 
     * @return
     */
    JSONObject findAllStockUpCloseAccount();

}
