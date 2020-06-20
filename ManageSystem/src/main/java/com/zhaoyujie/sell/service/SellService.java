package com.zhaoyujie.sell.service;

import com.alibaba.fastjson.JSONObject;

public interface SellService {

    /**
     * 增加销售信息
     * 
     * @param req
     * @return
     */
    JSONObject addSell(JSONObject req);

    /**
     * 查找某个商品的销售信息
     * 
     * @param req
     * @return
     */
    JSONObject findSell(JSONObject req);

    /**
     * 查找所有商品的销售信息
     * 
     * @param req
     * @return
     */
    JSONObject findAllSell();
}
