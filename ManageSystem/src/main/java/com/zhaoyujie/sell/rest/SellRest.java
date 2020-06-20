package com.zhaoyujie.sell.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhaoyujie.sell.service.SellService;
import com.zhaoyujie.util.CheckRequestUtil;

/**
 * Spring Boot 控制层
 * 
 * @author zhaoyujie
 *
 */
@RestController
@RequestMapping("/rest/sell/")
public class SellRest {

    private static final Logger log = LoggerFactory.getLogger(SellRest.class);

    @Autowired
    SellService sellService;

    /**
     * 增加商品销售信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("addSell")
    public JSONObject addSell(@RequestBody String reqStr) {
        log.info("Rest 调用增加商品销售信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "addSell", "request");
            response.put("data", sellService.addSell(req));
        } catch (Exception e) {
            log.error("调用增加商品销售信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "addSell");
        log.info("调用增加商品销售信息接口完成！");
        return return_data;
    }

    /**
     * 查找商品销售信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("findSell")
    public JSONObject findSell(@RequestBody String reqStr) {
        log.info("Rest 调用查找商品销售信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "findSell", "request");
            response.put("data", sellService.findSell(req));
        } catch (Exception e) {
            log.error("调用查找商品销售信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "findSell");
        log.info("调用查找商品销售信息接口完成！");
        return return_data;
    }

    /**
     * 查找所有的商品销售信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("findAllSell")
    public JSONObject findAllSupplier(@RequestBody String reqStr) {
        log.info("Rest 调用查找所有的商品销售信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            CheckRequestUtil.checkReqInterface(reqStr, "findAllSell", "request");
            response.put("data", sellService.findAllSell());
        } catch (Exception e) {
            log.error("调用查找所有的商品销售信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "findAllSell");
        log.info("调用查找所有的商品销售信息接口完成！");
        return return_data;
    }

}
