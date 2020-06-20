package com.zhaoyujie.stockupback.service.impl;

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
import com.zhaoyujie.stockupback.dao.StockUpBackCloseAccountDao;
import com.zhaoyujie.stockupback.dao.StockUpBackDao;
import com.zhaoyujie.stockupback.dao.StockUpBackTicketNumberDao;
import com.zhaoyujie.stockupback.domain.StockUpBack;
import com.zhaoyujie.stockupback.domain.StockUpBackCloseAccount;
import com.zhaoyujie.stockupback.domain.StockUpBackTicketNumber;
import com.zhaoyujie.stockupback.service.StockUpBackCloseAccountService;
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
public class StockUpBackCloseAccountServiceImpl implements StockUpBackCloseAccountService {

    private static final Logger log = LoggerFactory.getLogger(StockUpBackCloseAccountServiceImpl.class);

    @Autowired
    StockUpBackCloseAccountDao stockUpBackCloseAccountDao;

    @Autowired
    StockUpBackTicketNumberDao stockUpBackTicketNumberDao;

    @Autowired
    StockUpBackDao stockUpBackDao;

    @Autowired
    UserDao userDao;

    Random random = new Random();

    /**
     * Service 增加入库商品退货结账信息
     */
    @Override
    public JSONObject addStockUpBackCloseAccount(JSONObject req) {
        log.info("Service 调用增加入库商品退货结账信息接口");
        JSONObject response_data = new JSONObject();
        StockUpBack stockUpBack = new StockUpBack(); // 库存退货实体
        StockUpBackCloseAccount stockUpBackCloseAccount = new StockUpBackCloseAccount(); // 入库退货实体
        StockUpBackTicketNumber stockUpBackTicketNumber = new StockUpBackTicketNumber(); // 入库退货票号表实体
        User user = new User(); // 操作员实体

        // 获取前端信息
        String stockUpBackId = req.getString("stockUpBackId"); // 退货票号
        String closeAccount = req.getString("closeAccount"); // 本次结款
        String account = req.getString("account"); // 操作员账号
        String operator = req.getString("operator"); // 经手人

        try {
            // 校验退货票号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(stockUpBackId, "stockUpBackId");
            // 校验本次结款
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(closeAccount, "closeAccount");
            // 校验操作员账号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(account, "account");
            // 校验经手人
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(operator, "operator");

            // 将字符串类型转化为数值类型
            double closeAccountNum = Double.parseDouble(closeAccount); // 本次结款

            // 查找此入库信息是否存在
            stockUpBack = stockUpBackDao.findByStockUpBackIdAndDelFlag(stockUpBackId, true);
            if (null == stockUpBack) {
                throw new MyException(ExceptionType.NotFound, "此商品入库退货信息不存在，无法进行结账操作");
            }

            // 查找此操作员是否已存在
            user = userDao.findByAccount(account);
            if (null == user) {
                throw new MyException(ExceptionType.NotFound, "此操作员信息不存在，无法进行结账操作");
            }

            // 查找入库票号信息
            stockUpBackTicketNumber = stockUpBackTicketNumberDao.findByStockUpBack(stockUpBack);
            if (null == stockUpBackTicketNumber) {
                throw new MyException(ExceptionType.NotFound, "此商品入库退货票号信息不存在，无法进行结账操作");
            }

            // 判断是否结清
            if (stockUpBackTicketNumber.isPayFlag()) {
                throw new MyException(ExceptionType.OperationError, "已结清金额，无需进行结账操作");
            }

            // 结账金额是否大于退货商品的总价
            if (closeAccountNum > stockUpBackTicketNumber.getUncollected()) {
                throw new MyException(ExceptionType.FaildToConvert, "结账的数额大于未收余款的数额，商品退货结账操作被驳回");
            }

            // 为入库结账表实体赋值
            stockUpBackCloseAccount.setStockUpBackCloseAccountId("" + System.currentTimeMillis() + random.nextInt(10));
            stockUpBackCloseAccount.setStockUpBack(stockUpBack);// 入库退货表
            stockUpBackCloseAccount.setCloseAccount(closeAccountNum); // 本次结款
            stockUpBackCloseAccount.setCloseAccountTime(TimeFormat.getFormatStrInfo()); // 本次结款日期
            stockUpBackCloseAccount.setUser(user); // 操作员
            stockUpBackCloseAccount.setOperator(operator); // 经手人

            // 为入库退货票号表实体赋值
            stockUpBackTicketNumber.setReceipt(stockUpBackTicketNumber.getReceipt() + closeAccountNum); // 实收
            stockUpBackTicketNumber
                    .setUncollected(stockUpBackTicketNumber.getReceivable() - stockUpBackTicketNumber.getReceipt()); // 未收
            // 判断是否结清
            if (stockUpBackTicketNumber.getUncollected() == 0) {
                stockUpBackTicketNumber.setPayFlag(true);
            } else {
                stockUpBackTicketNumber.setPayFlag(false);
            }

            // 保存入库退货结账信息
            stockUpBackCloseAccountDao.save(stockUpBackCloseAccount);
            // 保存入库退货票号信息
            stockUpBackTicketNumberDao.save(stockUpBackTicketNumber);

            // 数据返回
            response_data.put("message", "入库商品退货结账操作成功！");
            response_data.put("res", true);

        } catch (MyException e) {
            log.error("addStockUpBackCloseAccount:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        } catch (Exception e) {
            log.error("addStockUpBackCloseAccount:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 查找所有商品的入库退货结账信息
     */
    @Override
    public JSONObject findAllStockUpBackCloseAccount() {
        log.info("Service 调用查找所有商品的入库退货结账信息接口");
        JSONObject response_data = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        List<StockUpBackCloseAccount> stockUpBackCloseAccountArr = new ArrayList<>();

        try {
            stockUpBackCloseAccountArr = stockUpBackCloseAccountDao.findByDelFlag(true);

            // 遍历所有的商品入库结账信息
            for (int i = 0; i < stockUpBackCloseAccountArr.size(); i++) {
                JSONObject data = new JSONObject();
                data.put("stockUpCloseAccountId", stockUpBackCloseAccountArr.get(i).getStockUpBackCloseAccountId());
                data.put("stockUpBackId", stockUpBackCloseAccountArr.get(i).getStockUpBack().getStockUpBackId());
                data.put("commodityName",
                        stockUpBackCloseAccountArr.get(i).getStockUpBack().getStock().getBrand().getCommodityName());
                data.put("closeAccount", stockUpBackCloseAccountArr.get(i).getCloseAccount());
                data.put("closeAccountTime", stockUpBackCloseAccountArr.get(i).getCloseAccountTime());
                data.put("userName", stockUpBackCloseAccountArr.get(i).getUser().getUserName());
                data.put("operator", stockUpBackCloseAccountArr.get(i).getOperator());

                // 将查找到的数据添加到 json 数组中
                jsonArr.add(data);
            }

            // 数据返回
            response_data.put("data", jsonArr);
            response_data.put("message", "所有商品的入库退货结账信息查找成功！");
            response_data.put("res", true);

        } catch (Exception e) {
            log.error("findAllStockUpBackCloseAccount:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

}
