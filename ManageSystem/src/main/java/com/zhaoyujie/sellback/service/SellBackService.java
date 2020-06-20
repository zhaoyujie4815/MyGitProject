package com.zhaoyujie.sellback.service;

import com.alibaba.fastjson.JSONObject;

public interface SellBackService {
    
    /**
     * 增加销售退货信息
     * 
     * @param req
     * @return
     */
    JSONObject addSellBack(JSONObject req);

    /**
     * 查找某个商品的销售退货信息
     * 
     * @param req
     * @return
     */
    JSONObject findSellBack(JSONObject req);

    /**
     * 查找所有商品的销售退货信息
     * 
     * @param req
     * @return
     */
    JSONObject findAllSellBack();
}
