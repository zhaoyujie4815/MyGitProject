package com.zhaoyujie.sellback.service.impl;

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
import com.zhaoyujie.sellback.dao.SellBackCloseAccountDao;
import com.zhaoyujie.sellback.dao.SellBackDao;
import com.zhaoyujie.sellback.dao.SellBackTicketNumberDao;
import com.zhaoyujie.sellback.domain.SellBack;
import com.zhaoyujie.sellback.domain.SellBackCloseAccount;
import com.zhaoyujie.sellback.domain.SellBackTicketNumber;
import com.zhaoyujie.sellback.service.SellBackCloseAccountService;
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
public class SellBackCloseAccountServiceImpl implements SellBackCloseAccountService {

    private static final Logger log = LoggerFactory.getLogger(SellBackCloseAccountServiceImpl.class);

    @Autowired
    SellBackCloseAccountDao sellBackCloseAccountDao;

    @Autowired
    SellBackTicketNumberDao sellBackTicketNumberDao;

    @Autowired
    SellBackDao sellBackDao;

    @Autowired
    UserDao userDao;

    Random random = new Random();

    /**
     * Service 增加商品销售退货结账信息
     */
    @Override
    public JSONObject addSellBackCloseAccount(JSONObject req) {
        log.info("Service 调用增加商品销售退货结账信息接口");
        JSONObject response_data = new JSONObject();
        SellBack sellBack = new SellBack(); // 销售退货表实体
        SellBackCloseAccount sellBackCloseAccount = new SellBackCloseAccount(); // 销售退货结账表实体
        SellBackTicketNumber sellBackTicketNumber = new SellBackTicketNumber(); // 销售退货票号表实体
        User user = new User(); // 操作员实体

        // 获取前端信息
        String sellBackId = req.getString("sellBackId"); // 销售退货编号
        String closeAccount = req.getString("closeAccount"); // 本次结款
        String account = req.getString("account"); // 操作员账号
        String operator = req.getString("operator"); // 经手人

        try {
            // 校验销售退货编号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(sellBackId, "sellBackId");
            // 校验本次结款
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(closeAccount, "closeAccount");
            // 校验操作员账号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(account, "account");
            // 校验经手人
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(operator, "operator");

            // 将字符串类型转化为数值类型
            double closeAccountNum = Double.parseDouble(closeAccount); // 本次结款

            // 查找此销售信息是否存在
            sellBack = sellBackDao.findBySellBackIdAndDelFlag(sellBackId, true);
            if (null == sellBack) {
                throw new MyException(ExceptionType.NotFound, "此商品的销售退货信息不存在，无法进行结账操作");
            }

            // 查找此操作员是否已存在
            user = userDao.findByAccount(account);
            if (null == user) {
                throw new MyException(ExceptionType.NotFound, "此操作员信息不存在，无法进行结账操作");
            }

            // 查找销售退货票号信息
            sellBackTicketNumber = sellBackTicketNumberDao.findBySellBack(sellBack);
            if (null == sellBackTicketNumber) {
                throw new MyException(ExceptionType.NotFound, "此商品的销售退货票号信息不存在，无法进行结账操作");
            }

            // 判断是否结清
            if (sellBackTicketNumber.isPayFlag()) {
                throw new MyException(ExceptionType.OperationError, "已结清金额，无法进行结账操作");
            }

            // 判断本次结款金额是否大于未付数额
            if (closeAccountNum > sellBackTicketNumber.getUnpaid()) {
                throw new MyException(ExceptionType.OperationError, "本次结款金额大于未付数额，销售退货结账操作被驳回");
            }

            // 为销售退货结账表实体赋值
            sellBackCloseAccount.setSellBackCloseAccountId("" + System.currentTimeMillis() + random.nextInt(10));
            sellBackCloseAccount.setSellBack(sellBack); // 销售退货表实体
            sellBackCloseAccount.setCloseAccount(closeAccountNum); // 本次结款
            sellBackCloseAccount.setCloseAccountTime(TimeFormat.getFormatStrInfo()); // 本次结款日期
            sellBackCloseAccount.setUser(user); // 操作员
            sellBackCloseAccount.setOperator(operator); // 经手人

            // 为销售退货票号表实体赋值
            sellBackTicketNumber.setPaid(sellBackTicketNumber.getPaid() + closeAccountNum); // 实付
            sellBackTicketNumber.setUnpaid(sellBackTicketNumber.getPayable() - sellBackTicketNumber.getPaid()); // 未付
            // 判断是否结清
            if (sellBackTicketNumber.getUnpaid() == 0) {
                sellBackTicketNumber.setPayFlag(true);
            } else {
                sellBackTicketNumber.setPayFlag(false);
            }

            // 保存销售退货结账信息
            sellBackCloseAccountDao.save(sellBackCloseAccount);

            // 保存销售退货票号信息
            sellBackTicketNumberDao.save(sellBackTicketNumber);

            // 数据返回
            response_data.put("message", "商品销售退货结账操作成功！");
            response_data.put("res", true);

        } catch (MyException e) {
            log.error("addsellBackCloseAccount:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        } catch (Exception e) {
            log.error("addsellBackCloseAccount:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 查找所有商品的销售退货结账信息
     */
    @Override
    public JSONObject findAllSellBackCloseAccount() {
        log.info("Service 调用查找所有商品的销售退货结账信息接口");
        JSONObject response_data = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        List<SellBackCloseAccount> sellBackCloseAccountArr = new ArrayList<>();

        try {
            sellBackCloseAccountArr = sellBackCloseAccountDao.findByDelFlag(true);

            // 遍历所有的商品销售退货结账信息
            for (int i = 0; i < sellBackCloseAccountArr.size(); i++) {
                JSONObject data = new JSONObject();
                data.put("sellBackCloseAccountId", sellBackCloseAccountArr.get(i).getSellBackCloseAccountId());
                data.put("sellBackId", sellBackCloseAccountArr.get(i).getSellBack().getSellBackId());
                data.put("commodityName", sellBackCloseAccountArr.get(i).getSellBack().getSell().getStock().getBrand()
                        .getCommodityName());
                data.put("closeAccount", sellBackCloseAccountArr.get(i).getCloseAccount());
                data.put("closeAccountTime", sellBackCloseAccountArr.get(i).getCloseAccountTime());
                data.put("userName", sellBackCloseAccountArr.get(i).getUser().getUserName());
                data.put("operator", sellBackCloseAccountArr.get(i).getOperator());

                // 将查找到的数据添加到 json 数组中
                jsonArr.add(data);
            }

            // 数据返回
            response_data.put("data", jsonArr);
            response_data.put("message", "所有商品的销售退货结账信息查找成功！");
            response_data.put("res", true);

        } catch (Exception e) {
            log.error("findAllSellBackCloseAccount:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

}
