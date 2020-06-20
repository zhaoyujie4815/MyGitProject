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
import com.zhaoyujie.brand.dao.BrandDao;
import com.zhaoyujie.customer.dao.CustomerDao;
import com.zhaoyujie.customer.domain.Customer;
import com.zhaoyujie.sell.dao.SellCloseAccountDao;
import com.zhaoyujie.sell.dao.SellDao;
import com.zhaoyujie.sell.dao.SellTicketNumberDao;
import com.zhaoyujie.sell.domain.Sell;
import com.zhaoyujie.sell.domain.SellCloseAccount;
import com.zhaoyujie.sell.domain.SellTicketNumber;
import com.zhaoyujie.sell.service.SellService;
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
public class SellServiceImpl implements SellService {

    private static final Logger log = LoggerFactory.getLogger(SellServiceImpl.class);

    @Autowired
    SellDao sellDao;

    @Autowired
    BrandDao brandDao;

    @Autowired
    StockDao stockDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    SellCloseAccountDao sellCloseAccountDao;

    @Autowired
    SellTicketNumberDao sellTicketNumberDao;

    @Autowired
    UserDao userDao;

    Random random = new Random();

    /**
     * Service 调用增加商品销售信息接口
     */
    @Override
    public JSONObject addSell(JSONObject req) {
        log.info("Service 调用增加商品销售信息接口");
        JSONObject response_data = new JSONObject();
        Stock stock = new Stock(); // 库存表实体
        Sell sell = new Sell(); // 销售表实体
        SellCloseAccount sellCloseAccount = new SellCloseAccount(); // 销售结账表实体
        SellTicketNumber sellTicketNumber = new SellTicketNumber(); // 销售票号表实体
        Customer customer = new Customer(); // 客户实体
        User user = new User(); // 操作员实体

        // 获取前端信息
        String commodityId = req.getString("commodityId"); // 库存商品编号
        String customerName = req.getString("customerName"); // 客户名称
        String price = req.getString("price"); // 销售单价
        String sellNumber = req.getString("sellNumber"); // 销售数量
        String closeAccount = req.getString("closeAccount"); // 本次结款
        String account = req.getString("account"); // 操作员账号
        String operator = req.getString("operator"); // 经手人
        String method = req.getString("method"); // 结算方式

        try {
            // 校验商品编号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(commodityId, "commodityId");
            // 校验客户名称
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(customerName, "customerName");
            // 校验销售单价
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(price, "price");
            // 校验销售数量
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(sellNumber, "sellNumber");
            // 校验本次结款
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(closeAccount, "closeAccount");
            // 校验操作员账号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(account, "account");
            // 校验经手人
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(operator, "operator");
            // 校验结算方式
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(method, "method");

            // 将字符串类型转化为数值类型
            int Num = Integer.parseInt(sellNumber); // 销售数量
            double priceDou = Double.parseDouble(price); // 销售单价
            double closeAccountDou = Double.parseDouble(closeAccount); // 本次结款

            // 查找该商品的库存信息
            stock = stockDao.findByCommodityIdAndDelFlag(commodityId, true);
            if (null == stock) {
                throw new MyException(ExceptionType.NotFound, "库存中无该商品的库存信息，商品销售操作失败");
            }
            // 验证商品的出货数量是否足够
            if (stock.getStockNumber() < Num) {
                throw new MyException(ExceptionType.FaildToConvert, "库存中该商品的库存数量不足，请减少该商品的销售数量后再试！");
            }

            // 验证本次结款金额是否大于应收款项的数额
            if (closeAccountDou > (priceDou * Num)) {
                throw new MyException(ExceptionType.FaildToConvert, "结账的数额大于应收款项的数额，商品销售操作被驳回！");
            }

            // 查找客户的信息
            customer = customerDao.findByCustomerNameAndDelFlag(customerName, true);
            if (null == customer) {
                throw new MyException(ExceptionType.NotFound, "该客户信息未找到，商品销售操作失败");
            }

            // 查找该操作员的信息
            user = userDao.findByAccount(account);
            if (user == null) {
                throw new MyException(ExceptionType.NotFound, "该操作员信息未找到，禁止商品销售操作");
            }

            // 为销售信息表实体赋值
            sell.setSellId("" + System.currentTimeMillis() + random.nextInt(10));
            sell.setStock(stock);
            sell.setCustomer(customer);
            sell.setPrice(priceDou);
            sell.setSellNumber(Num);
            sell.setSellTime(TimeFormat.getFormatStrInfo()); // 获取当前时间
            sell.setUser(user);
            sell.setOperator(operator);
            sell.setMethod(method);

            // 为销售信息结账表实体赋值
            sellCloseAccount.setSellCloseAccountId("" + System.currentTimeMillis() + random.nextInt(10));
            sellCloseAccount.setSell(sell);
            sellCloseAccount.setCloseAccount(closeAccountDou); // 本次结款
            sellCloseAccount.setCloseAccountTime(TimeFormat.getFormatStrInfo()); // 获取当前时间
            sellCloseAccount.setUser(user);
            sellCloseAccount.setOperator(operator);

            // 为销售票号表实体赋值
            sellTicketNumber.setSellTicketNumberId("" + System.currentTimeMillis() + random.nextInt(10));
            sellTicketNumber.setSell(sell);
            sellTicketNumber.setReceivable(Num * priceDou); // 应收 销售单价乘以销售数量
            sellTicketNumber.setReceipt(closeAccountDou); // 实收 本次结账
            sellTicketNumber.setUncollected((Num * priceDou) - closeAccountDou); // 未收 应收减去实收
            // 判断是否结清
            if (sellTicketNumber.getUncollected() == 0) {
                sellTicketNumber.setPayFlag(true);
            } else {
                sellTicketNumber.setPayFlag(false);
            }

            // 减少库存商品的数量
            stock.setStockNumber(stock.getStockNumber() - Num);
            stock.setAmount(stock.getStockNumber() * stock.getPrice());

            // 判断库存商品是否出完
            if (stock.getStockNumber() == 0) {
                // 为库存信息表实体赋值
                stock.setDelFlag(false);
                stock.setStockNumber(0);
                stock.setAmount(0);
            }

            // 保存销售信息
            sellDao.save(sell);
            // 保存销售结账信息
            sellCloseAccountDao.save(sellCloseAccount);
            // 保存销售票号信息
            sellTicketNumberDao.save(sellTicketNumber);
            // 保存库存信息
            stockDao.save(stock);

            // 数据返回
            response_data.put("message", "商品销售操作成功！");
            response_data.put("res", true);

        } catch (MyException e) {
            log.error("addSell:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        } catch (Exception e) {
            log.error("addSell:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 查找商品销售信息
     */
    @Override
    public JSONObject findSell(JSONObject req) {
        log.info("Service 调用查找商品销售信息接口");
        JSONObject response_data = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        Stock stock = new Stock(); // 库存表实体
        List<Sell> sellArr = new ArrayList<>();

        // 获取前端信息
        String commodityId = req.getString("commodityId"); // 库存商品编号

        try {
            // 校验库存商品编号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(commodityId, "commodityId");

            // 查找销售信息是否存在
            stock = stockDao.findByCommodityId(commodityId);
            sellArr = sellDao.findByStockAndDelFlag(stock, true);
            if (stock == null || sellArr == null) {
                throw new MyException(ExceptionType.NotFoundField, "未找到该商品的销售信息");
            }

            // 遍历所有的商品销售信息
            for (int i = 0; i < sellArr.size(); i++) {
                JSONObject data = new JSONObject();
                SellTicketNumber sellTicketNumber = sellTicketNumberDao.findBySell(sellArr.get(i));
                data.put("sellId", sellArr.get(i).getSellId());
                data.put("commodityId", sellArr.get(i).getStock().getCommodityId());
                data.put("commodityName", sellArr.get(i).getStock().getBrand().getCommodityName());
                data.put("customerName", sellArr.get(i).getCustomer().getCustomerName());
                data.put("price", sellArr.get(i).getPrice()); // 销售单价
                data.put("sellNumber", sellArr.get(i).getSellNumber()); // 销售数量
                data.put("sellTime", sellArr.get(i).getSellTime()); // 销售时间
                data.put("userName", sellArr.get(i).getUser().getUserName());
                data.put("payFlag", sellTicketNumber.isPayFlag()); // 是否结清
                data.put("receivable", sellTicketNumber.getReceivable()); // 应收
                data.put("receipt", sellTicketNumber.getReceipt()); // 实收
                data.put("uncollected", sellTicketNumber.getUncollected()); // 未收
                data.put("method", sellArr.get(i).getMethod());

                // 将查找到的数据添加到 josn 数组中
                jsonArr.add(data);
            }

            // 数据返回
            response_data.put("data", jsonArr);
            response_data.put("message", "商品销售信息查找成功！");
            response_data.put("res", true);

        } catch (MyException e) {
            log.error("findSell:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        } catch (Exception e) {
            log.error("findSell:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 查找所有商品的销售信息
     */
    @Override
    public JSONObject findAllSell() {
        log.info("Service 调用查找所有商品的销售信息接口");
        JSONObject response_data = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        List<Sell> sellArr = new ArrayList<>();

        try {
            sellArr = sellDao.findByDelFlag(true);

            // 遍历所有的商品销售信息
            for (int i = 0; i < sellArr.size(); i++) {
                JSONObject data = new JSONObject();
                SellTicketNumber sellTicketNumber = sellTicketNumberDao.findBySell(sellArr.get(i));
                data.put("sellId", sellArr.get(i).getSellId());
                data.put("commodityId", sellArr.get(i).getStock().getCommodityId());
                data.put("commodityName", sellArr.get(i).getStock().getBrand().getCommodityName());
                data.put("customerName", sellArr.get(i).getCustomer().getCustomerName());
                data.put("price", sellArr.get(i).getPrice());
                data.put("sellNumber", sellArr.get(i).getSellNumber());
                data.put("sellTime", sellArr.get(i).getSellTime());
                data.put("userName", sellArr.get(i).getUser().getUserName());
                data.put("payFlag", sellTicketNumber.isPayFlag()); // 是否结清
                data.put("receivable", sellTicketNumber.getReceivable()); // 应收
                data.put("receipt", sellTicketNumber.getReceipt()); // 实收
                data.put("uncollected", sellTicketNumber.getUncollected()); // 未收
                data.put("method", sellArr.get(i).getMethod());

                // 将查找到的数据添加到 josn 数组中
                jsonArr.add(data);
            }
            // 数据返回
            response_data.put("data", jsonArr);
            response_data.put("message", "所有商品的销售信息查找成功！");
            response_data.put("res", true);

        } catch (Exception e) {
            log.error("findAllSell:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

}
