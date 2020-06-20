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
import com.zhaoyujie.stock.dao.StockDao;
import com.zhaoyujie.stock.domain.Stock;
import com.zhaoyujie.stockupback.dao.StockUpBackCloseAccountDao;
import com.zhaoyujie.stockupback.dao.StockUpBackDao;
import com.zhaoyujie.stockupback.dao.StockUpBackTicketNumberDao;
import com.zhaoyujie.stockupback.domain.StockUpBack;
import com.zhaoyujie.stockupback.domain.StockUpBackCloseAccount;
import com.zhaoyujie.stockupback.domain.StockUpBackTicketNumber;
import com.zhaoyujie.stockupback.service.StockUpBackService;
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
public class StockUpBackServiceImpl implements StockUpBackService {

    private static final Logger log = LoggerFactory.getLogger(StockUpBackServiceImpl.class);

    @Autowired
    StockDao stockDao;

    @Autowired
    UserDao userDao;

    @Autowired
    StockUpBackDao stockUpBackDao;

    @Autowired
    StockUpBackCloseAccountDao stockUpBackCloseAccountDao;

    @Autowired
    StockUpBackTicketNumberDao stockUpBackTicketNumberDao;

    Random random = new Random();

    /**
     * Service 增加库存商品退货信息
     */
    @Override
    public JSONObject addStockUpBack(JSONObject req) {
        log.info("Service 调用增加库存商品退货信息接口");
        JSONObject response_data = new JSONObject();
        Stock stock = new Stock(); // 库存表实体
        StockUpBack stockUpBack = new StockUpBack(); // 库存退货实体
        StockUpBackCloseAccount stockUpBackCloseAccount = new StockUpBackCloseAccount(); // 入库退货实体
        StockUpBackTicketNumber stockUpBackTicketNumber = new StockUpBackTicketNumber(); // 入库退货票号表实体
        User user = new User(); // 操作员实体

        // 获取前端信息
        String commodityId = req.getString("commodityId"); // 库存商品编号
        String stockUpBackNumber = req.getString("stockUpBackNumber"); // 退货数量
        String closeAccount = req.getString("closeAccount"); // 本次结款
        String account = req.getString("account"); // 操作员账号
        String operator = req.getString("operator"); // 经手人
        String method = req.getString("method"); // 结算方式

        try {
            // 校验商品编号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(commodityId, "commodityId");
            // 校验退货数量
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(stockUpBackNumber, "stockUpBackNumber");
            // 校验退货数量
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(closeAccount, "closeAccount");
            // 校验操作员账号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(account, "account");
            // 校验经手人
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(operator, "operator");
            // 校验结算方式
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(method, "method");

            // 将字符串类型转化为数值类型
            int Num = Integer.parseInt(stockUpBackNumber);
            double closeAccountNum = Double.parseDouble(closeAccount); // 本次结款

            // 查找该商品的库存信息
            stock = stockDao.findByCommodityIdAndDelFlag(commodityId, true);
            if (null == stock) {
                throw new MyException(ExceptionType.NotFound, "库存中无该商品的库存信息，商品退货操作失败");
            }

            // 验证商品的库存数量是否小于退货数量
            if (stock.getStockNumber() < Num) {
                throw new MyException(ExceptionType.FaildToConvert, "库存中该商品的库存数量不足，请减少该商品的退货数量后再试！");
            }

            // 查找该操作员的信息
            user = userDao.findByAccount(account);
            if (user == null) {
                throw new MyException(ExceptionType.NotFound, "该操作员信息未找到，禁止商品退货操作");
            }

            // 结账金额是否大于退货商品的总价
            if (closeAccountNum > (stock.getPrice() * Num)) {
                throw new MyException(ExceptionType.FaildToConvert, "结账金额大于退货商品总价，商品退货操作被驳回");
            }

            // 为销售信息表实体赋值
            stockUpBack.setStockUpBackId("" + System.currentTimeMillis() + random.nextInt(10));
            stockUpBack.setStock(stock); // 库存实体
            stockUpBack.setStockUpBackNumber(Num); // 退货数量
            stockUpBack.setBackTime(TimeFormat.getFormatStrInfo()); // 获取当前时间
            stockUpBack.setUser(user); // 操作员
            stockUpBack.setOperator(operator); // 经手人
            stockUpBack.setMethod(method); // 结算方式

            // 为入库退货结账表实体赋值
            stockUpBackCloseAccount.setStockUpBackCloseAccountId("" + System.currentTimeMillis() + random.nextInt(10));
            stockUpBackCloseAccount.setStockUpBack(stockUpBack);
            stockUpBackCloseAccount.setCloseAccount(closeAccountNum);
            stockUpBackCloseAccount.setCloseAccountTime(TimeFormat.getFormatStrInfo());
            stockUpBackCloseAccount.setUser(user);
            stockUpBackCloseAccount.setOperator(operator);

            // 为入库结账票号表实体赋值
            stockUpBackTicketNumber.setStockUpBackTicketNumberId("" + System.currentTimeMillis() + random.nextInt(10));
            stockUpBackTicketNumber.setStockUpBack(stockUpBack);
            stockUpBackTicketNumber.setReceivable(Num * stock.getPrice()); // 应收
            stockUpBackTicketNumber.setReceipt(closeAccountNum); // 实收
            stockUpBackTicketNumber.setUncollected(stockUpBackTicketNumber.getReceivable() - closeAccountNum); // 未收
            // 判断是否收完款项
            if (stockUpBackTicketNumber.getUncollected() == 0) {
                stockUpBackTicketNumber.setPayFlag(true);
            } else {
                stockUpBackTicketNumber.setPayFlag(false);
            }

            if (stock.getStockNumber() - Num == 0) {
                stock.setDelFlag(false);
            }
            // 为库存信息表实体赋值
            stock.setStockNumber(stock.getStockNumber() - Num);
            stock.setAmount(stock.getStockNumber() * stock.getPrice());

            // 保存退货信息
            stockUpBackDao.save(stockUpBack);
            // 保存入库退货结账信息
            stockUpBackCloseAccountDao.save(stockUpBackCloseAccount);
            // 保存入库退货票号信息
            stockUpBackTicketNumberDao.save(stockUpBackTicketNumber);
            // 保存库存信息
            stockDao.save(stock);

            // 数据返回
            response_data.put("message", "商品退货操作成功！");
            response_data.put("res", true);

        } catch (MyException e) {
            log.error("addStockUpBack:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        } catch (Exception e) {
            log.error("addStockUpBack:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 查找库存中商品的退货信息
     */
    @Override
    public JSONObject findStockUpBack(JSONObject req) {
        log.info("Service 调用查找库存中商品的退货信息接口");
        JSONObject response_data = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        Stock stock = new Stock(); // 库存表实体
        List<StockUpBack> stockUpBackArr = new ArrayList<>();

        // 获取前端信息
        String commodityId = req.getString("commodityId"); // 库存商品编号

        try {
            // 校验库存商品编号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(commodityId, "commodityId");

            // 查找商品退货信息是否存在
            stock = stockDao.findByCommodityId(commodityId);
            stockUpBackArr = stockUpBackDao.findByStockAndDelFlag(stock, true);
            if (stock == null || stockUpBackArr == null) {
                throw new MyException(ExceptionType.NotFoundField, "未找到该商品的退货信息");
            }

            // 遍历仓库中所有商品的退货信息
            for (int i = 0; i < stockUpBackArr.size(); i++) {
                JSONObject data = new JSONObject();
                StockUpBackTicketNumber stockUpBackTicketNumber = stockUpBackTicketNumberDao
                        .findByStockUpBack(stockUpBackArr.get(i)); // 入库退货票号表实体
                data.put("stockUpBackId", stockUpBackArr.get(i).getStockUpBackId());
                data.put("commodityId", stockUpBackArr.get(i).getStock().getCommodityId());
                data.put("commodityName", stockUpBackArr.get(i).getStock().getBrand().getCommodityName());
                data.put("supplierName", stockUpBackArr.get(i).getStock().getBrand().getSupplier().getSupplierName());
                data.put("stockUpBackNumber", stockUpBackArr.get(i).getStockUpBackNumber());
                data.put("backTime", stockUpBackArr.get(i).getBackTime());
                data.put("userName", stockUpBackArr.get(i).getUser().getUserName());
                data.put("payFlag", stockUpBackTicketNumber.isPayFlag()); // 是否结清
                data.put("receivable", stockUpBackTicketNumber.getReceivable()); // 应收
                data.put("receipt", stockUpBackTicketNumber.getReceipt()); // 实收
                data.put("uncollected", stockUpBackTicketNumber.getUncollected()); // 未收
                data.put("method", stockUpBackArr.get(i).getMethod());

                // 将查找到的数据添加到 josn 数组中
                jsonArr.add(data);
            }
            // 数据返回
            response_data.put("data", jsonArr);
            response_data.put("message", "库存中商品的退货信息查找成功！");
            response_data.put("res", true);

        } catch (Exception e) {
            log.error("findStockUpBack:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 查找库存中所有商品的退货信息
     */
    @Override
    public JSONObject findAllStockUpBack() {
        log.info("Service 调用查找库存中所有商品的退货信息接口");
        JSONObject response_data = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        List<StockUpBack> stockUpBackArr = new ArrayList<>();

        try {
            stockUpBackArr = stockUpBackDao.findByDelFlag(true);

            // 遍历仓库中所有商品的退货信息
            for (int i = 0; i < stockUpBackArr.size(); i++) {
                JSONObject data = new JSONObject();
                StockUpBackTicketNumber stockUpBackTicketNumber = stockUpBackTicketNumberDao
                        .findByStockUpBack(stockUpBackArr.get(i)); // 入库退货票号表实体
                data.put("stockUpBackId", stockUpBackArr.get(i).getStockUpBackId());
                data.put("commodityId", stockUpBackArr.get(i).getStock().getCommodityId());
                data.put("commodityName", stockUpBackArr.get(i).getStock().getBrand().getCommodityName());
                data.put("supplierName", stockUpBackArr.get(i).getStock().getBrand().getSupplier().getSupplierName());
                data.put("stockUpBackNumber", stockUpBackArr.get(i).getStockUpBackNumber());
                data.put("backTime", stockUpBackArr.get(i).getBackTime());
                data.put("userName", stockUpBackArr.get(i).getUser().getUserName());
                data.put("payFlag", stockUpBackTicketNumber.isPayFlag()); // 是否结清
                data.put("receivable", stockUpBackTicketNumber.getReceivable()); // 应收
                data.put("receipt", stockUpBackTicketNumber.getReceipt()); // 实收
                data.put("uncollected", stockUpBackTicketNumber.getUncollected()); // 未收
                data.put("method", stockUpBackArr.get(i).getMethod());

                // 将查找到的数据添加到 josn 数组中
                jsonArr.add(data);
            }
            // 数据返回
            response_data.put("data", jsonArr);
            response_data.put("message", "库存中所有商品的退货信息查找成功！");
            response_data.put("res", true);

        } catch (Exception e) {
            log.error("findAllStockUpBack:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

}
