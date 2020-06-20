package com.zhaoyujie.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 检查前端的请求格式
 * 
 * @author zhaoyujie
 */
public class CheckRequestUtil {

    public static JSONObject checkReqInterface(String reqstr, String interf, String requestType) throws Exception {
        // 将 String 对象转化成一个 JSONObject 对象
        JSONObject reqData = JSON.parseObject(reqstr);

        // 初步校验前端格式
        String cmd = reqData.getString("cmd");
        String type = reqData.getString("type");
        
        // 前端请求格式是否正确
        if (!type.equals(requestType) || !cmd.equals(interf)) {
            throw new MyException(ExceptionType.NotFoundField, "Rest层(" + interf + ")请求出现异常！");
//            throw new Exception("Rest层(" + interf + ")请求出现异常！");
        }
        
        JSONObject request = reqData.getJSONObject("request");
        return request;
    }

}
