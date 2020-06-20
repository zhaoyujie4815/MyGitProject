package com.zhaoyujie.customer.service;

import com.alibaba.fastjson.JSONObject;

public interface CustomerService {
    
    /**
    * 增加客户
    * 
    * @param req
    * @return
    */
   JSONObject addCustomer(JSONObject req);
   
   /**
    * 删除客户信息
    * 
    * @return
    */
   JSONObject deleteCustomer(JSONObject req);
   
   /**
    * 查找单个客户
    * 
    * @param req
    * @return
    */
   JSONObject findCustomer(JSONObject req);
   
   /**
    * 查找所有的客户
    * 
    * @param req
    * @return
    */
   JSONObject findAllCustomer();
   
   /**
    * 修改客户信息
    * 
    * @param req
    * @return
    */
   JSONObject modifyCustomer(JSONObject req);
   
   /**
    * 查找所有客户的全称
    * 
    * @return
    */
   JSONObject getAllCustomerName();

}
