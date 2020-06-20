package com.zhaoyujie.stock.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhaoyujie.stock.service.StockService;
import com.zhaoyujie.util.CheckRequestUtil;

/**
 * Spring Boot 控制层
 * 
 * @author zhaoyujie
 *
 */
@RestController
@RequestMapping("/rest/stock/")
public class StockRest {

    private static final Logger log = LoggerFactory.getLogger(StockRest.class);

    @Autowired
    StockService stockService;

    /**
     * 删除商品库存信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("deleteStock")
    public JSONObject deleteStock(@RequestBody String reqStr) {
        log.info("Rest 调用删除商品库存信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "deleteStock", "request");
            response.put("data", stockService.deleteStock(req));
        } catch (Exception e) {
            log.error("调用删除商品库存信息接口：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "deleteStock");
        log.info("调用删除商品库存信息接口完成！");
        return return_data;
    }

//    /**
//     * 查找商品库存信息接口
//     * 
//     * @param reqStr
//     * @return
//     */
//    @RequestMapping("findStock")
//    public JSONObject findStock(@RequestBody String reqStr) {
//        log.info("Rest 调用查找商品库存信息接口!");
//        log.info("前端请求：" + reqStr);
//        JSONObject return_data = new JSONObject();
//        JSONObject response = new JSONObject();
//
//        /**
//         * 1、初步校验前端请求
//         */
//        try {
//            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "findStock", "request");
//            response.put("data", stockService.findStock(req));
//        } catch (Exception e) {
//            log.error("调用查找商品库存信息接口失败：" + e.getMessage());
//            response.put("exception", e.getMessage());
//            response.put("res", false);
//        }
//
//        /**
//         * 2、返回数据
//         */
//        return_data.put("response", response);
//        return_data.put("type", "response");
//        return_data.put("cmd", "findStock");
//        log.info("调用查找商品库存信息接口完成！");
//        return return_data;
//    }

    /**
     * 查找所有的库存信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("findAllStock")
    public JSONObject findAllSupplier(@RequestBody String reqStr) {
        log.info("Rest 调用查找所有的库存信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            CheckRequestUtil.checkReqInterface(reqStr, "findAllStock", "request");
            response.put("data", stockService.findAllStock());
        } catch (Exception e) {
            log.error("调用查找所有的库存信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "findAllStock");
        log.info("调用查找所有的库存信息接口完成！");
        return return_data;
    }
    
    /**
     * 修改库存信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("modifyStock")
    public JSONObject modifyStock(@RequestBody String reqStr) {
        log.info("Rest 调用修改库存信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "modifyStock", "request");
            response.put("data", stockService.modifyStock(req));
        } catch (Exception e) {
            log.error("调用修改库存信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "modifyStock");
        log.info("调用修改库存信息接口完成！");
        return return_data;
    }
}
