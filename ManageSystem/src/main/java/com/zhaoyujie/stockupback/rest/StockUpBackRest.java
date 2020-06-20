package com.zhaoyujie.stockupback.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhaoyujie.stockupback.service.StockUpBackService;
import com.zhaoyujie.util.CheckRequestUtil;

/**
 * Spring Boot 控制层
 * 
 * @author zhaoyujie
 *
 */
@RestController
@RequestMapping("/rest/stockUpBack/")
public class StockUpBackRest {

    private static final Logger log = LoggerFactory.getLogger(StockUpBackRest.class);
    
    @Autowired
    StockUpBackService stockUpBackService;
    
    /**
     * 增加库存商品退货信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("addStockUpBack")
    public JSONObject addStockUpBack(@RequestBody String reqStr) {
        log.info("Rest 调用增加库存商品退货信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "addStockUpBack", "request");
            response.put("data", stockUpBackService.addStockUpBack(req));
        } catch (Exception e) {
            log.error("调用增加库存商品退货信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "addStockUpBack");
        log.info("调用增加库存商品退货信息接口完成！");
        return return_data;
    }

    /**
     * 查找库存商品退货信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("findStockUpBack")
    public JSONObject findStockUpBack(@RequestBody String reqStr) {
        log.info("Rest 调用查找库存商品退货信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "findStockUpBack", "request");
            response.put("data", stockUpBackService.findStockUpBack(req));
        } catch (Exception e) {
            log.error("调用查找库存商品退货信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "findStockUpBack");
        log.info("调用查找库存商品退货信息接口完成！");
        return return_data;
    }

    /**
     * 查找所有的库存商品退货信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("findAllStockUpBack")
    public JSONObject findAllStockUpBack(@RequestBody String reqStr) {
        log.info("Rest 调用查找所有的库存商品退货信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            CheckRequestUtil.checkReqInterface(reqStr, "findAllStockUpBack", "request");
            response.put("data", stockUpBackService.findAllStockUpBack());
        } catch (Exception e) {
            log.error("调用查找所有的库存商品退货信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "findAllStockUpBack");
        log.info("调用查找所有的库存商品退货信息接口完成！");
        return return_data;
    }
}
