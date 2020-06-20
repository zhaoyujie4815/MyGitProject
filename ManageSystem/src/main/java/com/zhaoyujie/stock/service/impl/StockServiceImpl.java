package com.zhaoyujie.stock.service.impl;

import java.util.ArrayList;
import java.util.List;

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
import com.zhaoyujie.stock.service.StockService;
import com.zhaoyujie.util.CheckStringIsEmptyAndNullUtil;
import com.zhaoyujie.util.ExceptionType;
import com.zhaoyujie.util.MyException;

/**
 * Service 层实现类
 * 
 * @author zhaoyujie
 *
 */
@Service
@Transactional
public class StockServiceImpl implements StockService {

    private static final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);

    @Autowired
    StockDao stockDao;

    @Autowired
    BrandDao brandDao;

    /**
     * Service 删除商品库存信息
     */
    @Override
    public JSONObject deleteStock(JSONObject req) {
        log.info("Service 调用删除商品库存信息接口");
        JSONObject response_data = new JSONObject();
        Brand brand = new Brand();
        Stock stock = new Stock();

        // 获取前端信息
        String commodityId = req.getString("commodityId"); // 库存商品编号
        String commodityName = req.getString("commodityName"); // 商品名称

        try {
            // 校验库存商品编号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(commodityId, "commodityId");
            // 校验商品全称
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(commodityName, "commodityName");

            // 查找此商品是否存在
            brand = brandDao.findByCommodityName(commodityName);
            if (null == brand) {
                throw new MyException(ExceptionType.NotFoundField, "此商品不存在，无法进行删除操作");
            }

            // 查找该商品的库存信息
            stock = stockDao.findByCommodityIdAndDelFlag(commodityId, true);
            if (null == stock) {
                throw new MyException(ExceptionType.NotFoundField, "此商品的库存信息不存在或已被删除，无法进行删除操作");
            }

            // 删除该商品的库存信息
            stock.setDelFlag(false);
            stockDao.save(stock);

            // 数据返回
            response_data.put("message", "商品库存信息删除成功！");
            response_data.put("res", true);

        } catch (MyException e) {
            log.error("deleteStock:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        } catch (Exception e) {
            log.error("deleteStock:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

//    /**
//     * Service 查找商品库存信息
//     */
//    @Override
//    public JSONObject findStock(JSONObject req) {
//        log.info("Service 调用查找商品库存信息接口");
//        JSONObject response_data = new JSONObject();
//        JSONObject data = new JSONObject();
//        Brand brand = new Brand();
//        Stock stock = new Stock();
//        JSONArray jsonArr = new JSONArray();
//
//        // 获取前端信息
//        String commodityName = req.getString("commodityName"); // 商品名称
//
//        try {
//            // 校验商品全称
//            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(commodityName);
//
//            // 查找此商品是否存在
//            brand = brandDao.findByCommodityName(commodityName);
//            // 查找该商品的库存信息
//            stock = stockDao.findByBrandAndDelFlag(brand, true);
//            if (null == brand || null == stock) {
//                throw new MyException(ExceptionType.NotFoundField, "此商品的库存信息不存在或已被删除，查找失败");
//            }
//
//            
//            data.put("commodityId", stock.getCommodityId());
//            data.put("commodityName", stock.getBrand().getCommodityName());
//            data.put("easyName", stock.getBrand().getEasyName());
//            data.put("place", stock.getBrand().getPlace());
//            data.put("spec", stock.getBrand().getSpec());
//            data.put("unit", stock.getBrand().getUnit());
//            data.put("pack", stock.getBrand().getPack());
//            data.put("price", stock.getPrice());
//            data.put("stockNumber", stock.getStockNumber());
//            data.put("amount", stock.getAmount());
//
//            // 将查找到的数据添加到 josn 数组中
//            jsonArr.add(data);
//
//            // 数据返回
//            response_data.put("data", jsonArr);
//            response_data.put("message", "商品库存信息查找成功！");
//            response_data.put("res", true);
//
//        } catch (MyException e) {
//            log.error("findStock:" + e.getMessage());
//            response_data.put("res", false);
//            response_data.put("exception", e.getMessage());
//        } catch (Exception e) {
//            log.error("findStock:" + e.getMessage());
//            response_data.put("res", false);
//            response_data.put("exception", e.getMessage());
//        }
//
//        return response_data;
//    }

    /**
     * Service 查找所有库存信息
     */
    @Override
    public JSONObject findAllStock() {
        log.info("Service 调用查找所有库存信息接口");
        JSONObject response_data = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        List<Stock> stockArr = new ArrayList<>();

        try {

            // 查找所有的库存信息
            stockArr = stockDao.findByDelFlag(true);

            // 遍历所有的库存信息
            for (int i = 0; i < stockArr.size(); i++) {
                JSONObject data = new JSONObject();
                data.put("commodityId", stockArr.get(i).getCommodityId());
                data.put("commodityName", stockArr.get(i).getBrand().getCommodityName());
                data.put("easyName", stockArr.get(i).getBrand().getEasyName());
                data.put("place", stockArr.get(i).getBrand().getPlace());
                data.put("spec", stockArr.get(i).getBrand().getSpec());
                data.put("unit", stockArr.get(i).getBrand().getUnit());
                data.put("pack", stockArr.get(i).getBrand().getPack());
                data.put("price", stockArr.get(i).getPrice());
                data.put("stockNumber", stockArr.get(i).getStockNumber());
                data.put("amount", stockArr.get(i).getAmount());

                // 将查找到的数据添加到 josn 数组中
                jsonArr.add(data);
            }

            // 数据返回
            response_data.put("data", jsonArr);
            response_data.put("message", "所有的库存信息查找成功！");
            response_data.put("res", true);

        } catch (Exception e) {
            log.error("findAllStock:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 修改商品库存信息
     * 
     * 注：仅能修改商品单价
     */
    @Override
    public JSONObject modifyStock(JSONObject req) {
        log.info("Service 调用修改商品库存信息接口");
        JSONObject response_data = new JSONObject();
        Brand brand = new Brand();
        Stock stock = new Stock();

        // 获取前端信息
        // 获取前端信息
        String commodityId = req.getString("commodityId"); // 库存商品编号
        String commodityName = req.getString("commodityName"); // 商品名称
        String price = req.getString("price"); // 库存商品单价

        try {
            // 校验库存商品编号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(commodityId, "commodityId");
            // 校验商品全称
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(commodityName, "commodityName");
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(price, "price");

            // 将字符串类型转化为数值类型
            double priceInt = Double.parseDouble(price);

            // 查找此商品是否存在
            brand = brandDao.findByCommodityName(commodityName);
            if (null == brand) {
                throw new MyException(ExceptionType.NotFoundField, "此商品不存在，无法进行修改操作");
            }

            // 查找该商品的库存信息
            stock = stockDao.findByCommodityIdAndDelFlag(commodityId, true);
            if (null == stock) {
                throw new MyException(ExceptionType.NotFoundField, "此商品的库存信息不存在或已被删除，无法进行删除操作");
            }

            // 修改该商品的库存信息
            stock.setPrice(priceInt);
            stock.setAmount(stock.getPrice() * stock.getStockNumber());
            stockDao.save(stock);

            // 数据返回
            response_data.put("message", "商品库存信息修改成功！");
            response_data.put("res", true);

        } catch (MyException e) {
            log.error("modifyStock:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        } catch (Exception e) {
            log.error("modifyStock:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

}
