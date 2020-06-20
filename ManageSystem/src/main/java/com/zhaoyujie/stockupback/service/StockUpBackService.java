package com.zhaoyujie.stockupback.service;

import com.alibaba.fastjson.JSONObject;

public interface StockUpBackService {

    /**
     * 增加商品入库后退货的信息
     * 
     * @param req
     * @return
     */
    JSONObject addStockUpBack(JSONObject req);

    /**
     * 查找某个商品的入库后退货的信息
     * 
     * @param req
     * @return
     */
    JSONObject findStockUpBack(JSONObject req);

    /**
     * 查找所有商品的入库后退货的信息
     * 
     * @param req
     * @return
     */
    JSONObject findAllStockUpBack();
}
