package com.zhaoyujie.stockupback.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhaoyujie.stockupback.service.StockUpBackCloseAccountService;
import com.zhaoyujie.util.CheckRequestUtil;

/**
 * Spring Boot 控制层
 * 
 * @author zhaoyujie
 *
 */
@RestController
@RequestMapping("/rest/stockUpBackCloseAccount/")
public class StockUpBackCloseAccountRest {

    private static final Logger log = LoggerFactory.getLogger(StockUpBackCloseAccountRest.class);

    @Autowired
    StockUpBackCloseAccountService stockUpBackCloseAccountService;
    
    /**
     * 增加入库商品退货结账信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("addStockUpBackCloseAccount")
    public JSONObject addStockUpBackCloseAccount(@RequestBody String reqStr) {
        log.info("Rest 调用增加入库商品退货结账信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "addStockUpBackCloseAccount", "request");
            response.put("data", stockUpBackCloseAccountService.addStockUpBackCloseAccount(req));
        } catch (Exception e) {
            log.error("调用增加入库商品退货结账信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "addStockUpBackCloseAccount");
        log.info("调用增加入库商品退货结账信息接口完成！");
        return return_data;
    }

    

    /**
     * 查找所有的入库商品退货结账信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("findAllStockUpBackCloseAccount")
    public JSONObject findAllStockUpBackCloseAccount(@RequestBody String reqStr) {
        log.info("Rest 调用查找所有的入库商品退货结账信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            CheckRequestUtil.checkReqInterface(reqStr, "findAllStockUpBackCloseAccount", "request");
            response.put("data", stockUpBackCloseAccountService.findAllStockUpBackCloseAccount());
        } catch (Exception e) {
            log.error("调用查找所有的入库商品退货结账信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "findAllStockUpBackCloseAccount");
        log.info("调用查找所有的入库商品退货结账信息接口完成！");
        return return_data;
    }
}
