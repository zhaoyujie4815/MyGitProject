package com.zhaoyujie.customer.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhaoyujie.customer.service.CustomerService;
import com.zhaoyujie.util.CheckRequestUtil;

/**
 * Spring Boot 控制层
 * 
 * @author zhaoyujie
 *
 */
@RestController
@RequestMapping("/rest/customer/")
public class CustomerRest {
    
    private static final Logger log = LoggerFactory.getLogger(CustomerRest.class);
    
    @Autowired
    CustomerService customerService;
    
    /**
     * 增加客户信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("addCustomer")
    public JSONObject addCustomer(@RequestBody String reqStr) {
        log.info("Rest 调用增加客户信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "addCustomer", "request");
            response.put("data", customerService.addCustomer(req));
        } catch (Exception e) {
            log.error("调用增加客户信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "addCustomer");
        log.info("调用增加客户信息接口完成！");
        return return_data;
    }

    /**
     * 删除客户信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("deleteCustomer")
    public JSONObject deleteCustomer(@RequestBody String reqStr) {
        log.info("Rest 调用删除客户信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "deleteCustomer", "request");
            response.put("data", customerService.deleteCustomer(req));
        } catch (Exception e) {
            log.error("调用删除客户信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "deleteCustomer");
        log.info("调用删除客户信息接口完成！");
        return return_data;
    }

    /**
     * 查找客户信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("findCustomer")
    public JSONObject findCustomer(@RequestBody String reqStr) {
        log.info("Rest 调用查找客户信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "findCustomer", "request");
            response.put("data", customerService.findCustomer(req));
        } catch (Exception e) {
            log.error("调用查找客户信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "findCustomer");
        log.info("调用查找客户信息接口完成！");
        return return_data;
    }

    /**
     * 查找所有的客户信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("findAllCustomer")
    public JSONObject findAllSupplier(@RequestBody String reqStr) {
        log.info("Rest 调用查找所有的客户信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            CheckRequestUtil.checkReqInterface(reqStr, "findAllCustomer", "request");
            response.put("data", customerService.findAllCustomer());
        } catch (Exception e) {
            log.error("调用查找所有的客户信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "findAllCustomer");
        log.info("调用查找所有的客户信息接口完成！");
        return return_data;
    }

    /**
     * 修改客户信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("modifyCustomer")
    public JSONObject modifyCustomer(@RequestBody String reqStr) {
        log.info("Rest 调用修改客户信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "modifyCustomer", "request");
            response.put("data", customerService.modifyCustomer(req));
        } catch (Exception e) {
            log.error("调用修改客户信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "modifyCustomer");
        log.info("调用修改客户信息接口完成！");
        return return_data;
    }
    
    /**
     * 获取所有客户全称接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("getAllCustomerName")
    public JSONObject getAllCustomerName(@RequestBody String reqStr) {
        log.info("Rest 调用获取所有客户全称接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            CheckRequestUtil.checkReqInterface(reqStr, "getAllCustomerName", "request");
            response.put("data", customerService.getAllCustomerName());
        } catch (Exception e) {
            log.error("调用获取所有客户全称接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "getAllCustomerName");
        log.info("调用获取所有客户全称接口完成！");
        return return_data;
    }
}
