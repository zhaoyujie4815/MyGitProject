package com.zhaoyujie.supplier.service;

import com.alibaba.fastjson.JSONObject;

public interface SupplierService {
    
    /**
     * 增加供应商
     * 
     * @param req
     * @return
     */
    JSONObject addSupplier(JSONObject req);
    
    /**
     * 删除供应商信息
     * 
     * @param req
     * @return
     */
    JSONObject deleteSupplier(JSONObject req);
    
    /**
     * 查找单个供应商
     * 
     * @param req
     * @return
     */
    JSONObject findSupplier(JSONObject req);
    
    /**
     * 查找所有的供应商
     * 
     * @param req
     * @return
     */
    JSONObject findAllSupplier();
    
    /**
     * 修改供应商信息
     * 
     * @param req
     * @return
     */
    JSONObject modifySupplier(JSONObject req);
    
    /**
     * 获取所有的供应商姓名
     * 
     * @param req
     * @return
     */
    JSONObject getAllSupplierName();

}
