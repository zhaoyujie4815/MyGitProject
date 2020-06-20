package com.zhaoyujie.sell.service.impl;

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
import com.zhaoyujie.sell.dao.SellCloseAccountDao;
import com.zhaoyujie.sell.dao.SellDao;
import com.zhaoyujie.sell.dao.SellTicketNumberDao;
import com.zhaoyujie.sell.domain.Sell;
import com.zhaoyujie.sell.domain.SellCloseAccount;
import com.zhaoyujie.sell.domain.SellTicketNumber;
import com.zhaoyujie.sell.service.SellCloseAccountService;
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
public class SellCloseAccountServiceImpl implements SellCloseAccountService {

    private static final Logger log = LoggerFactory.getLogger(SellCloseAccountServiceImpl.class);

    @Autowired
    SellDao sellDao;

    @Autowired
    SellCloseAccountDao sellCloseAccountDao;

    @Autowired
    SellTicketNumberDao sellTicketNumberDao;

    @Autowired
    UserDao userDao;

    Random random = new Random();

    /**
     * Service 增加商品销售结账信息
     */
    @Override
    public JSONObject addSellCloseAccount(JSONObject req) {
        log.info("Service 调用增加商品销售结账信息接口");
        JSONObject response_data = new JSONObject();
        Sell sell = new Sell(); // 销售表实体
        SellCloseAccount sellCloseAccount = new SellCloseAccount(); // 销售结账表实体
        SellTicketNumber sellTicketNumber = new SellTicketNumber(); // 销售票号表实体
        User user = new User(); // 操作员实体

        // 获取前端信息
        String sellId = req.getString("sellId"); // 销售编号
        String closeAccount = req.getString("closeAccount"); // 本次结款
        String account = req.getString("account"); // 操作员账号
        String operator = req.getString("operator"); // 经手人

        try {
            // 校验销售编号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(sellId, "sellId");
            // 校验本次结款
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(closeAccount, "closeAccount");
            // 校验操作员账号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(account, "account");
            // 校验经手人
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(operator, "operator");

            // 将字符串类型转化为数值类型
            double closeAccountNum = Double.parseDouble(closeAccount); // 本次结款
            
            // 查找此销售信息是否存在
            sell = sellDao.findBySellIdAndDelFlag(sellId, true);
            if (null == sell) {
                throw new MyException(ExceptionType.NotFound, "此商品销售信息不存在，无法进行结账操作");
            }

            // 查找此操作员是否已存在
            user = userDao.findByAccount(account);
            if (null == user) {
                throw new MyException(ExceptionType.NotFound, "此操作员信息不存在，无法进行结账操作");
            }

            // 查找销售票号信息
            sellTicketNumber = sellTicketNumberDao.findBySell(sell);
            if (null == sellTicketNumber) {
                throw new MyException(ExceptionType.NotFound, "此商品销售票号信息不存在，无法进行结账操作");
            }
            
            // 判断是否结清
            if (sellTicketNumber.isPayFlag()) {
                throw new MyException(ExceptionType.OperationError, "已结清金额，无需进行结账操作");
            }
            
            // 判断本次结款金额是否大于应收余款的数额
            if(closeAccountNum > sellTicketNumber.getUncollected()) {
                throw new MyException(ExceptionType.OperationError, "本次结款金额大于应收余款的数额，销售结账操作被驳回");
            }

            // 为销售结账表实体赋值
            sellCloseAccount.setSellCloseAccountId("" + System.currentTimeMillis() + random.nextInt(10));
            sellCloseAccount.setSell(sell); // 销售信息表
            sellCloseAccount.setCloseAccount(closeAccountNum); // 本次结款
            sellCloseAccount.setCloseAccountTime(TimeFormat.getFormatStrInfo()); // 本次结款日期
            sellCloseAccount.setUser(user); // 操作员
            sellCloseAccount.setOperator(operator); // 经手人

            // 为销售票号表实体赋值
            sellTicketNumber.setReceipt(sellTicketNumber.getReceipt() + closeAccountNum); // 实收
            sellTicketNumber.setUncollected(sellTicketNumber.getReceivable() - sellTicketNumber.getReceipt()); // 未收
            // 判断是否结清
            if (sellTicketNumber.getUncollected() == 0) {
                sellTicketNumber.setPayFlag(true);
            } else {
                sellTicketNumber.setPayFlag(false);
            }

            // 保存销售结账信息
            sellCloseAccountDao.save(sellCloseAccount);

            // 保存销售票号信息
            sellTicketNumberDao.save(sellTicketNumber);

            // 数据返回
            response_data.put("message", "商品销售结账操作成功！");
            response_data.put("res", true);

        } catch (MyException e) {
            log.error("addSellCloseAccount:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        } catch (Exception e) {
            log.error("addSellCloseAccount:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 查找所有商品的销售结账信息
     */
    @Override
    public JSONObject findAllSellCloseAccount() {
        log.info("Service 调用查找所有商品的销售结账信息接口");
        JSONObject response_data = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        List<SellCloseAccount> sellCloseAccountArr = new ArrayList<>();

        try {
            sellCloseAccountArr = sellCloseAccountDao.findByDelFlag(true);

            // 遍历所有的商品销售结账信息
            for (int i = 0; i < sellCloseAccountArr.size(); i++) {
                JSONObject data = new JSONObject();
                data.put("sellCloseAccountId", sellCloseAccountArr.get(i).getSellCloseAccountId());
                data.put("sellId", sellCloseAccountArr.get(i).getSell().getSellId());
                data.put("commodityName",
                        sellCloseAccountArr.get(i).getSell().getStock().getBrand().getCommodityName());
                data.put("closeAccount", sellCloseAccountArr.get(i).getCloseAccount());
                data.put("closeAccountTime", sellCloseAccountArr.get(i).getCloseAccountTime());
                data.put("userName", sellCloseAccountArr.get(i).getUser().getUserName());
                data.put("operator", sellCloseAccountArr.get(i).getOperator());

                // 将查找到的数据添加到 json 数组中
                jsonArr.add(data);
            }

            // 数据返回
            response_data.put("data", jsonArr);
            response_data.put("message", "所有商品的销售结账信息查找成功！");
            response_data.put("res", true);

        } catch (Exception e) {
            log.error("findAllSellCloseAccount:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

}
