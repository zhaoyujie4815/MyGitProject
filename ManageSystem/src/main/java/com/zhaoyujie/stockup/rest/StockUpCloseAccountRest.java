package com.zhaoyujie.stockup.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhaoyujie.stockup.service.StockUpCloseAccountService;
import com.zhaoyujie.util.CheckRequestUtil;

/**
 * Spring Boot 控制层
 * 
 * @author zhaoyujie
 *
 */
@RestController
@RequestMapping("/rest/stockUpCloseAccount/")
public class StockUpCloseAccountRest {
    
    private static final Logger log = LoggerFactory.getLogger(StockUpCloseAccountRest.class);
    
    @Autowired
    StockUpCloseAccountService stockUpCloseAccountService;
    
    /**
     * 增加入库结账信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("addStockUpCloseAccount")
    public JSONObject addStockUpCloseAccount(@RequestBody String reqStr) {
        log.info("Rest 调用增加入库结账信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "addStockUpCloseAccount", "request");
            response.put("data", stockUpCloseAccountService.addStockUpCloseAccount(req));
        } catch (Exception e) {
            log.error("调用增加入库结账信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "addStockUpCloseAccount");
        log.info("调用增加入库结账信息接口完成！");
        return return_data;
    }

    /**
     * 查找入库结账信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("findStockUpCloseAccount")
    public JSONObject findStockUpCloseAccount(@RequestBody String reqStr) {
        log.info("Rest 调用查找入库结账信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "findStockUpCloseAccount", "request");
            response.put("data", stockUpCloseAccountService.findStockUpCloseAccount(req));
        } catch (Exception e) {
            log.error("调用查找入库结账信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "findStockUpCloseAccount");
        log.info("调用查找入库结账信息接口完成！");
        return return_data;
    }

    /**
     * 查找所有的入库结账信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("findAllStockUpCloseAccount")
    public JSONObject findAllStockUpCloseAccount(@RequestBody String reqStr) {
        log.info("Rest 调用查找所有的入库结账信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            CheckRequestUtil.checkReqInterface(reqStr, "findAllStockUpCloseAccount", "request");
            response.put("data", stockUpCloseAccountService.findAllStockUpCloseAccount());
        } catch (Exception e) {
            log.error("调用查找所有的入库结账信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "findAllStockUpCloseAccount");
        log.info("调用查找所有的入库结账信息接口完成！");
        return return_data;
    }
}
