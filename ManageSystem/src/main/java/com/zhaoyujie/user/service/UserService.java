package com.zhaoyujie.user.service;

import com.alibaba.fastjson.JSONObject;

public interface UserService {
    
    /**
     * 管理员登录
     * 
     * @param req
     * @return
     */
    JSONObject login(JSONObject req);
    
    /**
     * 修改管理员信息
     * 
     * @param req
     * @return
     */
    JSONObject modifyUser(JSONObject req);
    
}
