package com.zhaoyujie.stockup.service;

import com.alibaba.fastjson.JSONObject;

public interface StockUpService {
    
    /**
     * 增加商品入库信息
     * 
     * @param req
     * @return
     */
    JSONObject addStockUp(JSONObject req);

    /**
     * 查找某个商品的入库信息
     * 
     * @param req
     * @return
     */
    JSONObject findStockUp(JSONObject req);

    /**
     * 查找所有商品的入库信息
     * 
     * @param req
     * @return
     */
    JSONObject findAllStockUp();

}
