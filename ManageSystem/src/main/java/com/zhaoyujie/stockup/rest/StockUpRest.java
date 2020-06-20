package com.zhaoyujie.stockup.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhaoyujie.stockup.service.StockUpService;
import com.zhaoyujie.util.CheckRequestUtil;

/**
 * Spring Boot 控制层
 * 
 * @author zhaoyujie
 *
 */
@RestController
@RequestMapping("/rest/stockUp/")
public class StockUpRest {

    private static final Logger log = LoggerFactory.getLogger(StockUpRest.class);

    @Autowired
    StockUpService stockUpService;

    /**
     * 增加入库信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("addStockUp")
    public JSONObject addStockUp(@RequestBody String reqStr) {
        log.info("Rest 调用增加入库信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "addStockUp", "request");
            response.put("data", stockUpService.addStockUp(req));
        } catch (Exception e) {
            log.error("调用增加入库信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "addStockUp");
        log.info("调用增加入库信息接口完成！");
        return return_data;
    }

    /**
     * 查找入库信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("findStockUp")
    public JSONObject findStockUp(@RequestBody String reqStr) {
        log.info("Rest 调用查找入库信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "findStockUp", "request");
            response.put("data", stockUpService.findStockUp(req));
        } catch (Exception e) {
            log.error("调用查找入库信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "findStockUp");
        log.info("调用查找入库信息接口完成！");
        return return_data;
    }

    /**
     * 查找所有的入库信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("findAllStockUp")
    public JSONObject findAllStockUp(@RequestBody String reqStr) {
        log.info("Rest 调用查找所有的入库信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            CheckRequestUtil.checkReqInterface(reqStr, "findAllStockUp", "request");
            response.put("data", stockUpService.findAllStockUp());
        } catch (Exception e) {
            log.error("调用查找所有的入库信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "findAllStockUp");
        log.info("调用查找所有的入库信息接口完成！");
        return return_data;
    }

}
