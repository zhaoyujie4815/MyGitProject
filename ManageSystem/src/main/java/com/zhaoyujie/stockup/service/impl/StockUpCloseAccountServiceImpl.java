package com.zhaoyujie.stockup.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhaoyujie.stockup.dao.StockUpCloseAccountDao;
import com.zhaoyujie.stockup.dao.StockUpDao;
import com.zhaoyujie.stockup.dao.StockUpTicketNumberDao;
import com.zhaoyujie.stockup.domain.StockUp;
import com.zhaoyujie.stockup.domain.StockUpCloseAccount;
import com.zhaoyujie.stockup.domain.StockUpTicketNumber;
import com.zhaoyujie.stockup.service.StockUpCloseAccountService;
import com.zhaoyujie.user.dao.UserDao;
import com.zhaoyujie.user.domain.User;
import com.zhaoyujie.util.CheckStringIsEmptyAndNullUtil;
import com.zhaoyujie.util.ExceptionType;
import com.zhaoyujie.util.MyException;
import com.zhaoyujie.util.TimeFormat;

/**
 * Service 层实现类
 * 
 * @author zhaoyujie
 *
 */
@Service
@Transactional
public class StockUpCloseAccountServiceImpl implements StockUpCloseAccountService {

    private static final Logger log = LoggerFactory.getLogger(StockUpCloseAccountServiceImpl.class);

    @Autowired
    StockUpCloseAccountDao stockUpCloseAccountDao;

    @Autowired
    StockUpTicketNumberDao stockUpTicketNumberDao;

    @Autowired
    StockUpDao stockUpDao;

    @Autowired
    UserDao userDao;

    Random random = new Random();

    /**
     * Service 增加商品入库结账信息
     */
    @Override
    public JSONObject addStockUpCloseAccount(JSONObject req) {
        log.info("Service 调用增加商品入库结账信息接口");
        JSONObject response_data = new JSONObject();
        StockUp stockUp = new StockUp(); // 入库表实体
        StockUpCloseAccount stockUpCloseAccount = new StockUpCloseAccount(); // 入库结账表实体
        StockUpTicketNumber stockUpTicketNumber = new StockUpTicketNumber(); // 入库票号表实体
        User user = new User(); // 操作员实体

        // 获取前端信息
        String stockUpId = req.getString("stockUpId"); // 入库票号
        String closeAccount = req.getString("closeAccount"); // 本次结款
        String account = req.getString("account"); // 操作员账号
        String operator = req.getString("operator"); // 经手人

        try {
            // 校验入库票号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(stockUpId, "stockUpId");
            // 校验本次结款
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(closeAccount, "closeAccount");
            // 校验操作员账号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(account, "account");
            // 校验经手人
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(operator, "operator");

            // 将字符串类型转化为数值类型
            double closeAccountNum = Double.parseDouble(closeAccount); // 本次结款

            // 查找此入库信息是否存在
            stockUp = stockUpDao.findByStockUpIdAndDelFlag(stockUpId, true);
            if (null == stockUp) {
                throw new MyException(ExceptionType.NotFound, "此商品入库信息不存在，无法进行结账操作");
            }

            // 查找此操作员是否已存在
            user = userDao.findByAccount(account);
            if (null == user) {
                throw new MyException(ExceptionType.NotFound, "此操作员信息不存在，无法进行结账操作");
            }

            // 查找入库票号信息
            stockUpTicketNumber = stockUpTicketNumberDao.findByStockUp(stockUp);
            if (null == stockUpTicketNumber) {
                throw new MyException(ExceptionType.NotFound, "此入库票号信息不存在，无法进行结账操作");
            }
            
            // 判断账单是否结清
            if (stockUpTicketNumber.isPayFlag()) {
                throw new MyException(ExceptionType.OperationError, "已结清金额，无法进行结账操作");
            }
            
            // 结账金额是否大于未付金额
            if (closeAccountNum > stockUpTicketNumber.getUnpaid()) {
                throw new MyException(ExceptionType.OperationError, "此次结账金额大于未付金额，结账操作出现错误");
            }
            
            // 为入库结账表实体赋值
            stockUpCloseAccount.setStockUpCloseAccountId("" + System.currentTimeMillis() + random.nextInt(10));
            stockUpCloseAccount.setStockUp(stockUp); // 入库表
            stockUpCloseAccount.setCloseAccount(closeAccountNum); // 本次结款
            stockUpCloseAccount.setCloseAccountTime(TimeFormat.getFormatStrInfo()); // 本次结款日期
            stockUpCloseAccount.setUser(user); // 操作员
            stockUpCloseAccount.setOperator(operator); // 经手人

            // 为入库票号表实体赋值
            stockUpTicketNumber.setPaid(stockUpTicketNumber.getPaid() + closeAccountNum); // 实付
            stockUpTicketNumber.setUnpaid(stockUpTicketNumber.getPayable() - stockUpTicketNumber.getPaid()); // 未付
            // 判断是否结清
            if (stockUpTicketNumber.getUnpaid() == 0) {
                stockUpTicketNumber.setPayFlag(true);
            } else {
                stockUpTicketNumber.setPayFlag(false);
            }

            // 保存入库结账信息
            stockUpCloseAccountDao.save(stockUpCloseAccount);

            // 保存入库票号信息
            stockUpTicketNumberDao.save(stockUpTicketNumber);

            // 数据返回
            response_data.put("message", "入库商品结账操作成功！");
            response_data.put("res", true);

        } catch (MyException e) {
            log.error("addStockUpCloseAccount:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        } catch (Exception e) {
            log.error("addStockUpCloseAccount:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 查找所有商品的入库结账信息
     */
    @Override
    public JSONObject findStockUpCloseAccount(JSONObject req) {
        log.info("Service 调用查找所有商品的入库结账信息接口");
        JSONObject response_data = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        StockUp stockUp = new StockUp();
        List<StockUpCloseAccount> stockUpCloseAccountArr = new ArrayList<>();

        // 获取前端信息
        String stockUpId = req.getString("stockUpId"); // 入库票号

        try {

            // 校验入库票号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(stockUpId, "stockUpId");

            // 查找此入库信息是否存在
            stockUp = stockUpDao.findByStockUpIdAndDelFlag(stockUpId, true);
            stockUpCloseAccountArr = stockUpCloseAccountDao.findByStockUp(stockUp);
            if (null == stockUp || stockUpCloseAccountArr == null) {
                throw new MyException(ExceptionType.NotFound, "此商品入库结账信息不存在，查找所有商品的入库结账信息失败");
            }

            // 遍历所有的商品入库结账信息
            for (int i = 0; i < stockUpCloseAccountArr.size(); i++) {
                JSONObject data = new JSONObject();
                data.put("stockUpCloseAccountId", stockUpCloseAccountArr.get(i).getStockUpCloseAccountId());
                data.put("stockUpId", stockUpCloseAccountArr.get(i).getStockUp().getStockUpId());
                data.put("commodityName", stockUpCloseAccountArr.get(i).getStockUp().getBrand().getCommodityName());
                data.put("closeAccount", stockUpCloseAccountArr.get(i).getCloseAccount());
                data.put("closeAccountTime", stockUpCloseAccountArr.get(i).getCloseAccountTime());
                data.put("userName", stockUpCloseAccountArr.get(i).getUser().getUserName());
                data.put("operator", stockUpCloseAccountArr.get(i).getOperator());

                // 将查找到的数据添加到 json 数组中
                jsonArr.add(data);
            }

            // 数据返回
            response_data.put("data", jsonArr);
            response_data.put("message", "商品的入库结账信息查找成功！");
            response_data.put("res", true);

        } catch (Exception e) {
            log.error("findStockUpCloseAccount:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 查找所有商品的入库结账信息
     */
    @Override
    public JSONObject findAllStockUpCloseAccount() {
        log.info("Service 调用查找所有商品的入库结账信息接口");
        JSONObject response_data = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        List<StockUpCloseAccount> stockUpCloseAccountArr = new ArrayList<>();

        try {
            stockUpCloseAccountArr = stockUpCloseAccountDao.findByDelFlag(true);

            // 遍历所有的商品入库结账信息
            for (int i = 0; i < stockUpCloseAccountArr.size(); i++) {
                JSONObject data = new JSONObject();
                data.put("stockUpCloseAccountId", stockUpCloseAccountArr.get(i).getStockUpCloseAccountId());
                data.put("stockUpId", stockUpCloseAccountArr.get(i).getStockUp().getStockUpId());
                data.put("commodityName", stockUpCloseAccountArr.get(i).getStockUp().getBrand().getCommodityName());
                data.put("closeAccount", stockUpCloseAccountArr.get(i).getCloseAccount());
                data.put("closeAccountTime", stockUpCloseAccountArr.get(i).getCloseAccountTime());
                data.put("userName", stockUpCloseAccountArr.get(i).getUser().getUserName());
                data.put("operator", stockUpCloseAccountArr.get(i).getOperator());

                // 将查找到的数据添加到 json 数组中
                jsonArr.add(data);
            }

            // 数据返回
            response_data.put("data", jsonArr);
            response_data.put("message", "所有商品的入库结账信息查找成功！");
            response_data.put("res", true);

        } catch (Exception e) {
            log.error("findAllStockUpCloseAccount:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

}
