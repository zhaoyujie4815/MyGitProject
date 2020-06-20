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
import com.zhaoyujie.sell.dao.SellDao;
import com.zhaoyujie.sell.domain.Sell;
import com.zhaoyujie.sellback.dao.SellBackCloseAccountDao;
import com.zhaoyujie.sellback.dao.SellBackDao;
import com.zhaoyujie.sellback.dao.SellBackTicketNumberDao;
import com.zhaoyujie.sellback.domain.SellBack;
import com.zhaoyujie.sellback.domain.SellBackCloseAccount;
import com.zhaoyujie.sellback.domain.SellBackTicketNumber;
import com.zhaoyujie.sellback.service.SellBackService;
import com.zhaoyujie.stock.dao.StockDao;
import com.zhaoyujie.stock.domain.Stock;
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
public class SellBackServiceImpl implements SellBackService {

    private static final Logger log = LoggerFactory.getLogger(SellBackServiceImpl.class);

    @Autowired
    SellBackDao sellBackDao;

    @Autowired
    SellBackCloseAccountDao sellBackCloseAccountDao;

    @Autowired
    SellBackTicketNumberDao sellBackTicketNumberDao;

    @Autowired
    SellDao sellDao;

    @Autowired
    UserDao userDao;

    @Autowired
    StockDao stockDao;

    Random random = new Random();

    /**
     * Service 增加销售商品退货信息
     */
    @Override
    public JSONObject addSellBack(JSONObject req) {
        log.info("Service 调用增加销售商品退货信息接口");
        JSONObject response_data = new JSONObject();
        Sell sell = new Sell(); // 销售表实体
        SellBack sellBack = new SellBack(); // 销售退货表实体
        SellBackCloseAccount sellBackCloseAccount = new SellBackCloseAccount(); // 销售退货结账表实体
        SellBackTicketNumber sellBackTicketNumber = new SellBackTicketNumber(); // 销售退货票号表实体
        Stock stock = new Stock(); // 库存表实体
        User user = new User(); // 操作员实体

        // 获取前端信息
        String sellId = req.getString("sellId"); // 销售编号
        String sellbackNumber = req.getString("sellbackNumber"); // 退货数量
        String closeAccount = req.getString("closeAccount"); // 本次结款金额
        String account = req.getString("account"); // 操作员账号
        String operator = req.getString("operator"); // 经手人
        String method = req.getString("method"); // 结算方式

        try {
            // 校验销售编号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(sellId, "sellId");
            // 校验退货数量
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(sellbackNumber, "sellbackNumber");
            // 校验本次结款金额
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(closeAccount, "closeAccount");
            // 校验操作员账号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(account, "account");
            // 校验经手人
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(operator, "operator");
            // 校验结算方式
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(method, "method");

            // 将字符串类型转化为数值类型
            int Num = Integer.parseInt(sellbackNumber); // 退货数量
            double closeAccountDou = Double.parseDouble(closeAccount); // 本次结款实付金额

            // 查找该商品的销售信息
            sell = sellDao.findBySellIdAndDelFlag(sellId, true);
            if (null == sell) {
                throw new MyException(ExceptionType.NotFound, "无法找到该商品的销售信息，销售商品退货操作失败");
            }
            // 验证商品的销售数量是否小于销售退货的数量
            if (sell.getSellNumber() < Num) {
                throw new MyException(ExceptionType.FaildToConvert, "商品的销售退货的数量大于商品销售的数量，销售商品退货操作失败");
            }
            
            // 验证本次结款金额是否大于销售商品退货的总价
            if (closeAccountDou > (sell.getPrice() * Num)) {
                throw new MyException(ExceptionType.FaildToConvert, "本次结款金额大于销售商品退货的总价，销售商品退货操作被驳回");
            }

            // 查找该操作员的信息
            user = userDao.findByAccount(account);
            if (user == null) {
                throw new MyException(ExceptionType.NotFound, "该操作员信息未找到，禁止商品退货操作");
            }

            // 为销售退货信息表实体赋值
            sellBack.setSellBackId("" + System.currentTimeMillis() + random.nextInt(10));
            sellBack.setSell(sell); // 销售信息表实体
            sellBack.setSellbackNumber(Num); // 退货数量
            sellBack.setBackTime(TimeFormat.getFormatStrInfo()); // 获取当前时间
            sellBack.setUser(user); // 操作员
            sellBack.setOperator(operator); // 经手人
            sellBack.setMethod(method); // 结算方式

            // 为销售退货结账表实体赋值
            sellBackCloseAccount.setSellBackCloseAccountId("" + System.currentTimeMillis() + random.nextInt(10));
            sellBackCloseAccount.setSellBack(sellBack); // 销售退货表
            sellBackCloseAccount.setCloseAccount(closeAccountDou); // 本次结款金额
            sellBackCloseAccount.setCloseAccountTime(TimeFormat.getFormatStrInfo()); // 获取当前时间
            sellBackCloseAccount.setUser(user); // 操作员
            sellBackCloseAccount.setOperator(operator); // 经手人

            // 为销售退货票号表实体赋值
            sellBackTicketNumber.setSellBackTickNumberId("" + System.currentTimeMillis() + random.nextInt(10));
            sellBackTicketNumber.setSellBack(sellBack); // 销售退货表
            sellBackTicketNumber.setPayable(Num * sellBackTicketNumber.getSellBack().getSell().getPrice()); // 应付
                                                                                                            // 销售退货数量乘以销售单价
            sellBackTicketNumber.setPaid(closeAccountDou); // 实付 本次结账金额
            sellBackTicketNumber.setUnpaid(sellBackTicketNumber.getPayable() - closeAccountDou); // 未付 应付减去实付
            // 判断是否结清
            if (sellBackTicketNumber.getUnpaid() == 0) {
                sellBackTicketNumber.setPayFlag(true);
            } else {
                sellBackTicketNumber.setPayFlag(false);
            }

            // 验证该商品的库存信息是否被删除
            stock = sell.getStock();
            if (!stock.isDelFlag()) {
                // 销售退回的商品，再次入库
                stock.setStockNumber(Num); // 库存数量改变
                stock.setAmount(Num * stock.getPrice()); // 库存金额改变
                stock.setDelFlag(true); // 将库存中该商品的信息重新设置为未被删除的状态
            }

            // 保存退货信息
            sellBackDao.save(sellBack);
            // 保存销售退货结账信息
            sellBackCloseAccountDao.save(sellBackCloseAccount);
            // 保存销售退货票号信息
            sellBackTicketNumberDao.save(sellBackTicketNumber);
            // 保存库存信息
            stockDao.save(stock);

            // 数据返回
            response_data.put("message", "销售商品退货操作成功！");
            response_data.put("res", true);

        } catch (MyException e) {
            log.error("addSellBack:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        } catch (Exception e) {
            log.error("addSellBack:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 查找销售商品的退货信息
     */
    @Override
    public JSONObject findSellBack(JSONObject req) {
        log.info("Service 调用查找销售商品的退货信息接口");
        JSONObject response_data = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        Sell sell = new Sell(); // 销售表实体
        List<SellBack> sellBackArr = new ArrayList<>();

        // 获取前端信息
        String sellId = req.getString("sellId"); // 销售编号

        try {
            // 校验销售编号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(sellId, "sellId");

            // 查找该商品的销售信息
            sell = sellDao.findBySellIdAndDelFlag(sellId, true);
            sellBackArr = sellBackDao.findBySell(sell);
            if (null == sell || sellBackArr == null) {
                throw new MyException(ExceptionType.NotFound, "无法找到该商品的销售信息，销售商品退货操作失败");
            }

            // 遍历所有销售商品的退货信息
            for (int i = 0; i < sellBackArr.size(); i++) {
                JSONObject data = new JSONObject();
                SellBackTicketNumber sellBackTicketNumber = sellBackTicketNumberDao.findBySellBack(sellBackArr.get(i));
                data.put("sellBackId", sellBackArr.get(i).getSellBackId());
                data.put("commodityId", sellBackArr.get(i).getSell().getStock().getCommodityId());
                data.put("commodityName", sellBackArr.get(i).getSell().getStock().getBrand().getCommodityName());
                data.put("customerName", sellBackArr.get(i).getSell().getCustomer().getCustomerName());
                data.put("sellbackNumber", sellBackArr.get(i).getSellbackNumber());
                data.put("backTime", sellBackArr.get(i).getBackTime()); // 销售退货日期
                data.put("payable", sellBackTicketNumber.getPayable()); // 应付
                data.put("paid", sellBackTicketNumber.getPaid()); // 实付
                data.put("unpaid", sellBackTicketNumber.getUnpaid()); // 未付
                data.put("payFlag", sellBackTicketNumber.isPayFlag()); // 是否结清
                data.put("method", sellBackArr.get(i).getMethod()); // 支付方式

                // 将查找到的数据添加到 josn 数组中
                jsonArr.add(data);
            }
            // 数据返回
            response_data.put("data", jsonArr);
            response_data.put("message", "销售商品的退货信息查找成功！");
            response_data.put("res", true);

        } catch (Exception e) {
            log.error("findAllSellBack:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 查找所有销售商品的退货信息
     */
    @Override
    public JSONObject findAllSellBack() {
        log.info("Service 调用查找所有销售商品的退货信息接口");
        JSONObject response_data = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        List<SellBack> sellBackArr = new ArrayList<>();

        try {
            sellBackArr = sellBackDao.findByDelFlag(true);

            // 遍历所有销售商品的退货信息
            for (int i = 0; i < sellBackArr.size(); i++) {
                JSONObject data = new JSONObject();
                SellBackTicketNumber sellBackTicketNumber = sellBackTicketNumberDao.findBySellBack(sellBackArr.get(i));
                data.put("sellBackId", sellBackArr.get(i).getSellBackId());
                data.put("commodityId", sellBackArr.get(i).getSell().getStock().getCommodityId());
                data.put("commodityName", sellBackArr.get(i).getSell().getStock().getBrand().getCommodityName());
                data.put("customerName", sellBackArr.get(i).getSell().getCustomer().getContactName());
                data.put("sellbackNumber", sellBackArr.get(i).getSellbackNumber());
                data.put("backTime", sellBackArr.get(i).getBackTime()); // 销售退货日期
                data.put("payable", sellBackTicketNumber.getPayable()); // 应付
                data.put("paid", sellBackTicketNumber.getPaid()); // 实付
                data.put("unpaid", sellBackTicketNumber.getUnpaid()); // 未付
                data.put("payFlag", sellBackTicketNumber.isPayFlag()); // 是否结清
                data.put("method", sellBackArr.get(i).getMethod()); // 支付方式

                // 将查找到的数据添加到 josn 数组中
                jsonArr.add(data);
            }
            // 数据返回
            response_data.put("data", jsonArr);
            response_data.put("message", "所有销售的商品退货信息查找成功！");
            response_data.put("res", true);

        } catch (Exception e) {
            log.error("findAllSellBack:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

}
