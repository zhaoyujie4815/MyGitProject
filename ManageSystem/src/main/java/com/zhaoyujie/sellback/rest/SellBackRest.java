package com.zhaoyujie.sellback.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhaoyujie.sellback.service.SellBackService;
import com.zhaoyujie.util.CheckRequestUtil;

/**
 * Spring Boot 控制层
 * 
 * @author zhaoyujie
 *
 */
@RestController
@RequestMapping("/rest/sellBack/")
public class SellBackRest {
    
    private static final Logger log = LoggerFactory.getLogger(SellBackRest.class);
    
    @Autowired
    SellBackService sellBackService;
    
    /**
     * 增加销售商品退货信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("addSellBack")
    public JSONObject addSellBack(@RequestBody String reqStr) {
        log.info("Rest 调用增加销售商品退货信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "addSellBack", "request");
            response.put("data", sellBackService.addSellBack(req));
        } catch (Exception e) {
            log.error("调用增加销售商品退货信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "addSellBack");
        log.info("调用增加销售商品退货信息接口完成！");
        return return_data;
    }

    /**
     * 查找销售商品退货信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("findSellBack")
    public JSONObject findSellBack(@RequestBody String reqStr) {
        log.info("Rest 调用查找销售商品退货信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "findSellBack", "request");
            response.put("data", sellBackService.findSellBack(req));
        } catch (Exception e) {
            log.error("调用查找销售商品退货信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "findSellBack");
        log.info("调用查找销售商品退货信息接口完成！");
        return return_data;
    }

    /**
     * 查找所有的销售商品退货信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("findAllSellBack")
    public JSONObject findAllSupplier(@RequestBody String reqStr) {
        log.info("Rest 调用查找所有的销售商品退货信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            CheckRequestUtil.checkReqInterface(reqStr, "findAllSellBack", "request");
            response.put("data", sellBackService.findAllSellBack());
        } catch (Exception e) {
            log.error("调用查找所有的销售商品退货信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "findAllSellBack");
        log.info("调用查找所有的销售商品退货信息接口完成！");
        return return_data;
    }
}
