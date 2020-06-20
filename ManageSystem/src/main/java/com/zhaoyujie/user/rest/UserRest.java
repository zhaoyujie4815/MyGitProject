package com.zhaoyujie.user.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhaoyujie.user.service.UserService;
import com.zhaoyujie.util.CheckRequestUtil;

/**
 * Spring Boot 控制层
 * 
 * @author zhaoyujie
 */
@RestController
@RequestMapping("/rest/user/")
public class UserRest {

    private static final Logger log = LoggerFactory.getLogger(UserRest.class);

    @Autowired
    UserService userService;

    /**
     * 管理员登录接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("login")
    public JSONObject login(@RequestBody String reqStr) {
        log.info("Rest 调用管理员登录接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();
        
        /**
         * 1、初步校验前端请求
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "login", "request");
            response.put("data", userService.login(req));
        } catch (Exception e) {
            log.error("调用管理员登录接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }
        
        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "login");
        log.info("调用管理员登录接口完成！");
        return return_data;
    }
    
    /**
     * 修改管理员信息接口
     * 
     * @param reqStr
     * @return
     */
    @RequestMapping("modifyUser")
    public JSONObject modifyUser(@RequestBody String reqStr) {
        log.info("Rest 调用修改管理员接口!");
        log.info("前端请求：" + reqStr);
        JSONObject return_data = new JSONObject();
        JSONObject response = new JSONObject();
        
        /**
         * 1、初步校验前端请求格式
         */
        try {
            JSONObject req = CheckRequestUtil.checkReqInterface(reqStr, "modifyUser", "request");
            response.put("data", userService.modifyUser(req));
        } catch (Exception e) {
            log.error("调用修改管理员接口失败：" + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("res", false);
        }
        
        /**
         * 2、返回数据
         */
        return_data.put("response", response);
        return_data.put("type", "response");
        return_data.put("cmd", "modifyUser");
        log.info("调用修改管理员接口完成！");
        return return_data;
    }
    

}
