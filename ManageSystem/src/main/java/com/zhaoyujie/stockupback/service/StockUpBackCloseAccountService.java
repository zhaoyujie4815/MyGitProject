package com.zhaoyujie.stockupback.service;

import com.alibaba.fastjson.JSONObject;

public interface StockUpBackCloseAccountService {
    
    /**
     * 增加入库退货结账信息
     * 
     * @param req
     * @return
     */
    JSONObject addStockUpBackCloseAccount(JSONObject req);
        
    /**
     * 查询所有的入库退货结账信息
     * 
     * @return
     */
    JSONObject findAllStockUpBackCloseAccount();

}
