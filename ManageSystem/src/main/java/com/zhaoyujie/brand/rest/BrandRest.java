package com.zhaoyujie.brand.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhaoyujie.brand.service.BrandService;
import com.zhaoyujie.util.CheckRequestUtil;

/**
 * Spring Boot 控制层
 * 
 * @author zhaoyujie
 *
 */
@RestController
@RequestMapping("/rest/brand/")
public class BrandRest {
    
    private static final Logger log = LoggerFactory.getLogger(BrandRest.class);
    
    @Autowired
    BrandService brandService;
    
    /**
     * 增加商品信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("addBrand")
    public JSONObject addBrand(@RequestBody String reqStr) {
        log.info("Rest 调用增加商品信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "addBrand", "request");
            response.put("data", brandService.addBrand(req));
        } catch (Exception e) {
            log.error("调用增加商品信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "addBrand");
        log.info("调用增加商品信息接口完成！");
        return return_data;
    }

    /**
     * 删除商品信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("deleteBrand")
    public JSONObject deleteBrand(@RequestBody String reqStr) {
        log.info("Rest 调用删除商品信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "deleteBrand", "request");
            response.put("data", brandService.deleteBrand(req));
        } catch (Exception e) {
            log.error("调用删除商品信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "deleteBrand");
        log.info("调用删除商品信息接口完成！");
        return return_data;
    }

    

    /**
     * 查找所有的商品信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("findAllBrand")
    public JSONObject findAllBrand(@RequestBody String reqStr) {
        log.info("Rest 调用查找所有的商品信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            CheckRequestUtil.checkReqInterface(reqStr, "findAllBrand", "request");
            response.put("data", brandService.findAllBrand());
        } catch (Exception e) {
            log.error("调用查找所有的商品信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "findAllBrand");
        log.info("调用查找所有的商品信息接口完成！");
        return return_data;
    }

    /**
     * 修改商品信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("modifyBrand")
    public JSONObject modifyBrand(@RequestBody String reqStr) {
        log.info("Rest 调用修改商品信息接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "modifyBrand", "request");
            response.put("data", brandService.modifyBrand(req));
        } catch (Exception e) {
            log.error("调用修改商品信息接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "modifyBrand");
        log.info("调用修改商品信息接口完成！");
        return return_data;
    }
    
    /**
     * 获取所有商品全称接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("getAllCommodityName")
    public JSONObject getAllCommodityName(@RequestBody String reqStr) {
        log.info("Rest 调用获取所有商品全称接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();

        /**
         * 1、初步校验前端请求
         */
        try {
            CheckRequestUtil.checkReqInterface(reqStr, "getAllCommodityName", "request");
            response.put("data", brandService.getAllCommodityName());
        } catch (Exception e) {
            log.error("调用获取所有商品全称接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }

        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "getAllCommodityName");
        log.info("调用获取所有商品全称接口完成！");
        return return_data;
    }
}
