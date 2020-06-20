package com.zhaoyujie.supplier.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhaoyujie.supplier.service.SupplierService;
import com.zhaoyujie.util.CheckRequestUtil;

/**
 * Spring Boot 控制层
 * 
 * @author zhaoyujie
 *
 */
@RestController
@RequestMapping("/rest/supplier/")
public class SupplierRest {

    private static final Logger log = LoggerFactory.getLogger(SupplierRest.class);

    @Autowired
    SupplierService supplierService;

    /**
     * 增加供应商信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("addSupplier")
    public JSONObject addSupplier(@RequestBody String reqStr) {
        log.info("Rest 调用增加供应商信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "addSupplier", "request");
            response.put("data", supplierService.addSupplier(req));
        } catch (Exception e) {
            log.error("调用增加供应商信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "addSupplier");
        log.info("调用增加供应商信息接口完成！");
        return return_data;
    }

    /**
     * 删除供应商信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("deleteSupplier")
    public JSONObject deleteSupplier(@RequestBody String reqStr) {
        log.info("Rest 调用删除供应商信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "deleteSupplier", "request");
            response.put("data", supplierService.deleteSupplier(req));
        } catch (Exception e) {
            log.error("调用删除供应商信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "deleteSupplier");
        log.info("调用删除供应商信息接口完成！");
        return return_data;
    }

    /**
     * 查找供应商信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("findSupplier")
    public JSONObject findSupplier(@RequestBody String reqStr) {
        log.info("Rest 调用查找供应商信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "findSupplier", "request");
            response.put("data", supplierService.findSupplier(req));
        } catch (Exception e) {
            log.error("调用查找供应商信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "findSupplier");
        log.info("调用查找供应商信息接口完成！");
        return return_data;
    }

    /**
     * 查找所有的供应商信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("findAllSupplier")
    public JSONObject findAllSupplier(@RequestBody String reqStr) {
        log.info("Rest 调用查找所有的供应商信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            CheckRequestUtil.checkReqInterface(reqStr, "findAllSupplier", "request");
            response.put("data", supplierService.findAllSupplier());
        } catch (Exception e) {
            log.error("调用查找所有的供应商信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "findAllSupplier");
        log.info("调用查找所有的供应商信息接口完成！");
        return return_data;
    }

    /**
     * 修改供应商信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("modifySupplier")
    public JSONObject modifySupplier(@RequestBody String reqStr) {
        log.info("Rest 调用修改供应商信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "modifySupplier", "request");
            response.put("data", supplierService.modifySupplier(req));
        } catch (Exception e) {
            log.error("调用修改供应商信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "modifySupplier");
        log.info("调用修改供应商信息接口完成！");
        return return_data;
    }
    
    /**
     * 获取所有供应商全称接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("getAllSupplierName")
    public JSONObject getAllSupplierName(@RequestBody String reqStr) {
        log.info("Rest 调用获取所有供应商全称接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            CheckRequestUtil.checkReqInterface(reqStr, "getAllSupplierName", "request");
            response.put("data", supplierService.getAllSupplierName());
        } catch (Exception e) {
            log.error("调用获取所有供应商全称接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "getAllSupplierName");
        log.info("调用获取所有供应商全称接口完成！");
        return return_data;
    }
}
