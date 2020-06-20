package com.zhaoyujie.stock.service;

import com.alibaba.fastjson.JSONObject;

public interface StockService {

    /**
     * 删除库存信息
     * 
     * @param req
     * @return
     */
    JSONObject deleteStock(JSONObject req);

//    /**
//     * 查找某个商品的库存信息
//     * 
//     * @param req
//     * @return
//     */
//    JSONObject findStock(JSONObject req);

    /**
     * 查找所有商品的库存
     * 
     * @return
     */
    JSONObject findAllStock();

    /**
     * 手动修改库存信息
     * 
     * 注意: 仅当库存信息和实际信息不符时修改
     * 
     * @param req
     * @return
     */
    JSONObject modifyStock(JSONObject req);
}
