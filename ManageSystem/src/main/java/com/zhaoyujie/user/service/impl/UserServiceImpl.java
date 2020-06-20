package com.zhaoyujie.user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.zhaoyujie.user.dao.UserDao;
import com.zhaoyujie.user.domain.User;
import com.zhaoyujie.user.service.UserService;
import com.zhaoyujie.util.CheckStringIsEmptyAndNullUtil;
import com.zhaoyujie.util.ExceptionType;
import com.zhaoyujie.util.MyException;

/**
 * 服务层
 * 
 * @author zhaoyujie
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserDao userDao;

    /**
     * Service 登录方法
     * 
     * @param req 前端传过来的 JSON 对象
     * @return JSONObject
     */
    @Override
    public JSONObject login(JSONObject req) {
        log.info("Service 调用登录接口");
        JSONObject response_data = new JSONObject();
        User user = new User();

        /**
         * 1、获取前端数据
         */
        String account = req.getString("account");
        String password = req.getString("password");

        try {
            /**
             * 2、校验前端数据
             */
            // 校验账号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(account, "account");

            // 校验密码
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(password, "password");

            /**
             * 3、在数据库中查找此账号的信息
             */
            user = userDao.findByAccount(account);
            // 账号查找
            if (null == user) {
                throw new MyException(ExceptionType.LookupDatabaseIsEmpty, "请确认账号无误后再试！");
            }

            // 密码比对
            if (!password.equals(user.getPassword())) {
                throw new MyException(ExceptionType.Inconsistency, "密码输入错误！");
            }

            /**
             * 4、数据返回
             */
            response_data.put("userName", user.getUserName());
            response_data.put("message", "管理员登录成功！");
            response_data.put("res", true);

        } catch (MyException e) {
            log.error("login:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        } catch (Exception e) {
            log.error("login:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }
        return response_data;
    }

    /**
     * Service 修改管理员信息
     * 
     * @param req 前端传过来的 JSON 对象
     * @return JSONObject
     */
    @Override
    public JSONObject modifyUser(JSONObject req) {
        log.info("Service 调用修改管理员信息接口！");
        JSONObject response_data = new JSONObject();
        User user = new User();

        /**
         * 1、获取前端数据
         */
        String account = req.getString("account");
        String password = req.getString("password");

        try {
            /**
             * 2、校验前端数据
             */
            // 校验账号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(account, "account");

            // 校验密码
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(password, "password");

            /**
             * 3、查找该账号对应的信息
             */
            user = userDao.findByAccount(account);
            // 账号查找
            if (null == user) {
                throw new MyException(ExceptionType.LookupDatabaseIsEmpty, "此账号未注册或已被删除");
            }

            /**
             * 4、将数据保存到数据库中
             */
            user.setPassword(password);
            userDao.save(user);

            /**
             * 5、数据返回
             */
            response_data.put("message", "修改管理员信息成功！");
            response_data.put("res", true);
        } catch (MyException e) {
            log.error("modifyUser:" + e.getMessage());
            response_data.put("exception", e.getMessage());
            response_data.put("res", false);
        } catch (Exception e) {
            log.error("modifyUser:" + e.getMessage());
            response_data.put("exception", e.getMessage());
            response_data.put("res", false);
        }

        return response_data;
    }

}
