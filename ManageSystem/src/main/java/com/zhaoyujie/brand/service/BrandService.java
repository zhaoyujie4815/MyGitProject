package com.zhaoyujie.brand.service;

import com.alibaba.fastjson.JSONObject;

public interface BrandService {
    
    /**
     * 增加商品信息
     * 
     * @param req
     * @return
     */
    JSONObject addBrand(JSONObject req);
    
    /**
     * 删除商品信息
     * 
     * @param req
     * @return
     */
    JSONObject deleteBrand(JSONObject req);
    
    /**
     * 查询所有的商品信息
     * 
     * @return
     */
    JSONObject findAllBrand();
    
    /**
     * 修改商品信息
     * 
     * @param req
     * @return
     */
    JSONObject modifyBrand(JSONObject req);
    
    /**
     * 查询所有商品的全称
     * 
     * @return
     */
    JSONObject getAllCommodityName();
}
