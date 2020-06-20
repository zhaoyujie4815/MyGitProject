package com.zhaoyujie.sell.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhaoyujie.sell.service.SellCloseAccountService;
import com.zhaoyujie.util.CheckRequestUtil;

/**
 * Spring Boot 控制层
 * 
 * @author zhaoyujie
 *
 */
@RestController
@RequestMapping("/rest/sellCloseAccount/")
public class SellCloseAccountServiceRest {
    
    private static final Logger log = LoggerFactory.getLogger(SellCloseAccountServiceRest.class);
    
    @Autowired
    SellCloseAccountService sellCloseAccountService;
    
    /**
     * 增加销售商品结账信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("addSellCloseAccount")
    public JSONObject addSellCloseAccount(@RequestBody String reqStr) {
        log.info("Rest 调用增加销售商品结账信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "addSellCloseAccount", "request");
            response.put("data", sellCloseAccountService.addSellCloseAccount(req));
        } catch (Exception e) {
            log.error("调用增加销售商品结账信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "addSellCloseAccount");
        log.info("调用增加销售商品结账信息接口完成！");
        return return_data;
    }

    

    /**
     * 查找所有的销售商品结账信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("findAllSellCloseAccount")
    public JSONObject findAllSellCloseAccount(@RequestBody String reqStr) {
        log.info("Rest 调用查找所有的销售商品结账信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            CheckRequestUtil.checkReqInterface(reqStr, "findAllSellCloseAccount", "request");
            response.put("data", sellCloseAccountService.findAllSellCloseAccount());
        } catch (Exception e) {
            log.error("调用查找所有的销售商品结账信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "findAllSellCloseAccount");
        log.info("调用查找所有的销售商品结账信息接口完成！");
        return return_data;
    }
}
