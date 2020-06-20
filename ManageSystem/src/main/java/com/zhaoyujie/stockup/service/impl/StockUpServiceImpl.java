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
import com.zhaoyujie.brand.dao.BrandDao;
import com.zhaoyujie.brand.domain.Brand;
import com.zhaoyujie.stock.dao.StockDao;
import com.zhaoyujie.stock.domain.Stock;
import com.zhaoyujie.stockup.dao.StockUpCloseAccountDao;
import com.zhaoyujie.stockup.dao.StockUpDao;
import com.zhaoyujie.stockup.dao.StockUpTicketNumberDao;
import com.zhaoyujie.stockup.domain.StockUp;
import com.zhaoyujie.stockup.domain.StockUpCloseAccount;
import com.zhaoyujie.stockup.domain.StockUpTicketNumber;
import com.zhaoyujie.stockup.service.StockUpService;
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
public class StockUpServiceImpl implements StockUpService {

    private static final Logger log = LoggerFactory.getLogger(StockUpServiceImpl.class);

    @Autowired
    StockUpDao stockUpDao;

    @Autowired
    BrandDao brandDao;

    @Autowired
    UserDao userDao;

    @Autowired
    StockDao stockDao;

    @Autowired
    StockUpTicketNumberDao stockUpTicketNumberDao;

    @Autowired
    StockUpCloseAccountDao stockUpCloseAccountDao;

    Random random = new Random();

    /**
     * Service 增加商品入库信息
     */
    @Override
    public JSONObject addStockUp(JSONObject req) {
        log.info("Service 调用增加商品入库信息接口");
        JSONObject response_data = new JSONObject();
        Stock stock = new Stock(); // 库存表实体
        Stock stockNew = new Stock(); // 库存表实体
        StockUp stockUp = new StockUp(); // 入库表实体
        List<Stock> stockArr = new ArrayList<Stock>();
        StockUpCloseAccount stockUpCloseAccount = new StockUpCloseAccount(); // 入库结账表实体
        StockUpTicketNumber stockUpTicketNumber = new StockUpTicketNumber(); // 入库票号表实体
        Brand brand = new Brand(); // 商品实体
        User user = new User(); // 操作员实体

        // 获取前端信息
        String commodityName = req.getString("commodityName"); // 商品名称
        String stockUpNumber = req.getString("stockUpNumber"); // 入库数量
        String price = req.getString("price"); // 入库商品单价
        String paid = req.getString("paid"); // 入库商品实付金额
        String account = req.getString("account"); // 操作员账号
        String operator = req.getString("operator"); // 经手人
        String method = req.getString("method"); // 结算方式

        try {
            // 校验商品全称
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(commodityName, "commodityName");
            // 校验入库数量
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(stockUpNumber, "stockUpNumber");
            // 校验入库商品单价
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(price, "price");
            // 校验入库商品实付金额
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(paid, "paid");
            // 校验操作员账号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(account, "account");
            // 校验经手人
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(operator, "operator");
            // 校验结算方式
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(method, "method");

            // 将字符串类型转化为数值类型
            int Num = Integer.parseInt(stockUpNumber); // 入库数量
            double priceDou = Double.parseDouble(price); // 入库商品单价
            double paidDou = Double.parseDouble(paid); // 商品入库实付金额

            // 商品入库实付金额是否大于应付金额
            if (paidDou > (Num * priceDou)) {
                throw new MyException(ExceptionType.OperationError, "商品入库实付金额大于应付金额，商品入库操作失败");
            }

            // 查找此操作员是否已存在
            user = userDao.findByAccount(account);
            if (null == user) {
                throw new MyException(ExceptionType.AlreadyExist, "此操作员信息不存在，商品入库操作失败");
            }
            // 查找此商品是否存在
            brand = brandDao.findByCommodityName(commodityName);
            if (null == brand) {
                throw new MyException(ExceptionType.AlreadyExist, "此商品信息不存在，商品入库操作失败");
            }

            // 为入库信息表实体赋值
            stockUp.setStockUpId("" + System.currentTimeMillis() + random.nextInt(10));
            stockUp.setBrand(brand); // 商品
            stockUp.setStockUpNumber(Num); // 商品入库数量
            stockUp.setPrice(priceDou); // 商品入库单价
            stockUp.setStockUpTime(TimeFormat.getFormatStrInfo()); // 获取当前时间
            stockUp.setUser(user); // 操作员
            stockUp.setOperator(operator); // 经手人
            stockUp.setMethod(method); // 结算方式

            // 保存入库信息
            stockUpDao.save(stockUp);

            // 为入库结账表实体赋值
            stockUpCloseAccount.setStockUpCloseAccountId("" + System.currentTimeMillis() + random.nextInt(10));
            stockUpCloseAccount.setStockUp(stockUp); // 入库表
            stockUpCloseAccount.setCloseAccount(paidDou); // 本次结款
            stockUpCloseAccount.setCloseAccountTime(TimeFormat.getFormatStrInfo()); // 本次结款日期
            stockUpCloseAccount.setUser(user); // 操作员
            stockUpCloseAccount.setOperator(operator); // 经手人

            // 保存入库结账信息
            stockUpCloseAccountDao.save(stockUpCloseAccount);

            // 为入库票号表实体赋值
            stockUpTicketNumber.setStockUpTicketNumberId("" + System.currentTimeMillis() + random.nextInt(10));
            stockUpTicketNumber.setStockUp(stockUp); // 入库表
            stockUpTicketNumber.setPayable(Num * priceDou); // 应付
            stockUpTicketNumber.setPaid(paidDou); // 实付
            stockUpTicketNumber.setUnpaid((Num * priceDou) - paidDou); // 未付
            // 判断是否结清
            if (stockUpTicketNumber.getUnpaid() == 0) {
                stockUpTicketNumber.setPayFlag(true);
            } else {
                stockUpTicketNumber.setPayFlag(false);
            }

            // 保存入库票号信息
            stockUpTicketNumberDao.save(stockUpTicketNumber);

            // 验证库存中是否已有此商品
            stockArr = stockDao.findByBrandAndDelFlag(brand, true);
            for (int i = 0; i < stockArr.size(); i++) {
                stock = stockArr.get(i);
                if (priceDou == stock.getPrice()) {
                    stock.setStockNumber(Num + stock.getStockNumber());
                    stock.setAmount(priceDou * stock.getStockNumber());

                    // 保存库存信息
                    stockDao.save(stock);

                    // 数据返回
                    response_data.put("message", "商品入库操作成功！");
                    response_data.put("res", true);
                    return response_data;
                }
            }
            // 为库存信息表实体赋值
            stockNew.setCommodityId("" + System.currentTimeMillis() + random.nextInt(10));
            stockNew.setBrand(brand);
            stockNew.setPrice(priceDou);
            stockNew.setStockNumber(Num);
            stockNew.setAmount(stockNew.getPrice() * stockNew.getStockNumber());

            // 保存库存信息
            stockDao.save(stockNew);

            // 数据返回
            response_data.put("message", "商品入库操作成功！");
            response_data.put("res", true);

        } catch (MyException e) {
            log.error("addStockUp:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        } catch (Exception e) {
            log.error("addStockUp:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 查找商品入库信息
     */
    @Override
    public JSONObject findStockUp(JSONObject req) {
        log.info("Service 调用查找商品入库信息接口");
        JSONObject response_data = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        List<StockUp> stockUpsList = new ArrayList<>();
        Brand brand = new Brand();

        // 获取前端信息
        String commodityName = req.getString("commodityName"); // 商品全称

        try {
            // 校验商品全称
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(commodityName, "commodityName");

            // 查找入库信息是否存在
            brand = brandDao.findByCommodityName(commodityName);
            stockUpsList = stockUpDao.findByBrandAndDelFlag(brand, true);
            if (null == brand || stockUpsList == null) {
                throw new MyException(ExceptionType.NotFoundField, "未找到该商品的入库信息");
            }

            // 遍历所有的商品入库信息
            for (int i = 0; i < stockUpsList.size(); i++) {
                JSONObject data = new JSONObject();
                StockUpTicketNumber stockUpTicketNumber = stockUpTicketNumberDao.findByStockUp(stockUpsList.get(i));
                data.put("stockUpId", stockUpsList.get(i).getStockUpId()); // 入库票号
                data.put("commodityName", stockUpsList.get(i).getBrand().getCommodityName()); // 商品名称
                data.put("stockUpNumber", stockUpsList.get(i).getStockUpNumber()); // 入库数量
                data.put("price", stockUpsList.get(i).getPrice()); // 入库单价
                data.put("stockUpTime", stockUpsList.get(i).getStockUpTime()); // 入库时间
                data.put("userName", stockUpsList.get(i).getUser().getUserName()); // 操作员姓名
                data.put("payFlag", stockUpTicketNumber.isPayFlag()); // 是否结清
                data.put("payable", stockUpTicketNumber.getPayable()); // 应付
                data.put("paid", stockUpTicketNumber.getPaid()); // 实付
                data.put("unpaid", stockUpTicketNumber.getUnpaid()); // 未付
                data.put("method", stockUpsList.get(i).getMethod()); // 支付方式

                // 将查找到的数据添加到 josn 数组中
                jsonArr.add(data);
            }

            // 数据返回
            response_data.put("data", jsonArr);
            response_data.put("message", "商品入库信息查找成功！");
            response_data.put("res", true);

        } catch (MyException e) {
            log.error("findStockUp:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        } catch (Exception e) {
            log.error("findStockUp:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 查找所有的商品入库信息
     */
    @Override
    public JSONObject findAllStockUp() {
        log.info("Service 调用查找所有商品的入库信息接口");
        JSONObject response_data = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        List<StockUp> stockUpArr = new ArrayList<>();

        try {
            stockUpArr = stockUpDao.findByDelFlag(true);

            // 遍历所有的商品入库信息
            for (int i = 0; i < stockUpArr.size(); i++) {
                JSONObject data = new JSONObject();
                StockUpTicketNumber stockUpTicketNumber = stockUpTicketNumberDao.findByStockUp(stockUpArr.get(i));
                data.put("stockUpId", stockUpArr.get(i).getStockUpId()); // 入库票号
                data.put("commodityName", stockUpArr.get(i).getBrand().getCommodityName()); // 商品名称
                data.put("stockUpNumber", stockUpArr.get(i).getStockUpNumber()); // 入库数量
                data.put("price", stockUpArr.get(i).getPrice()); // 入库单价
                data.put("stockUpTime", stockUpArr.get(i).getStockUpTime()); // 入库时间
                data.put("userName", stockUpArr.get(i).getUser().getUserName()); // 操作员姓名
                data.put("payFlag", stockUpTicketNumber.isPayFlag()); // 是否结清
                data.put("payable", stockUpTicketNumber.getPayable()); // 应付
                data.put("paid", stockUpTicketNumber.getPaid()); // 实付
                data.put("unpaid", stockUpTicketNumber.getUnpaid()); // 未付
                data.put("method", stockUpArr.get(i).getMethod()); // 支付方式

                // 将查找到的数据添加到 json 数组中
                jsonArr.add(data);
            }

            // 数据返回
            response_data.put("data", jsonArr);
            response_data.put("message", "所有商品的入库信息查找成功！");
            response_data.put("res", true);

        } catch (Exception e) {
            log.error("findAllCustomer:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

}
