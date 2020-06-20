package com.zhaoyujie.brand.service.impl;

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
import com.zhaoyujie.brand.service.BrandService;
import com.zhaoyujie.supplier.dao.SupplierDao;
import com.zhaoyujie.supplier.domain.Supplier;
import com.zhaoyujie.util.CheckStringIsEmptyAndNullUtil;
import com.zhaoyujie.util.ExceptionType;
import com.zhaoyujie.util.MyException;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    private static final Logger log = LoggerFactory.getLogger(BrandServiceImpl.class);

    @Autowired
    BrandDao brandDao;

    @Autowired
    SupplierDao supplierDao;

    Random random = new Random();

    /**
     * Service 增加商品信息
     */
    @Override
    public JSONObject addBrand(JSONObject req) {
        log.info("Service 调用增加商品信息接口");
        JSONObject response_data = new JSONObject();
        Brand brand = new Brand();
        Supplier supplier = new Supplier();

        // 获取前端信息
        String commodityName = req.getString("commodityName"); // 商品名称
        String easyName = req.getString("easyName"); // 简称
        String place = req.getString("place"); // 产地
        String unit = req.getString("unit"); // 单位
        String spec = req.getString("spec"); // 规格
        String pack = req.getString("pack"); // 包装
        String lotNumber = req.getString("lotNumber"); // 批号
        String approvalNumber = req.getString("approvalNumber"); // 批准文号
        String supplierName = req.getString("supplierName"); // 供应商全称
        String remark = req.getString("remark"); // 备注

        try {
            // 校验商品全称
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(commodityName, "commodityName");
            // 校验简称
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(easyName, "easyName");
            // 校验产地
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(place, "place");
            // 校验单位
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(unit, "unit");
            // 校验规格
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(spec, "spec");
            // 校验包装
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(pack, "pack");
            // 校验批号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(lotNumber, "lotNumber");
            // 校验批准文号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(approvalNumber, "approvalNumber");
            // 校验供应商全称
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(supplierName, "supplierName");
            // 校验备注
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(remark, "remark");

            // 查找此供应商是否已存在
            supplier = supplierDao.findBySupplierNameAndDelFlag(supplierName, true);
            if (null == supplier) {
                throw new MyException(ExceptionType.NotFound, "此供应商信息不存在，添加商品信息操作失败");
            }
            // 查找此商品是否存在
            if (null != brandDao.findByCommodityName(commodityName)) {
                throw new MyException(ExceptionType.AlreadyExist, "此商品信息已存在，添加商品信息操作失败");
            }

            // 为实体赋值
            brand.setBrandId("" + System.currentTimeMillis() + random.nextInt(10));
            brand.setCommodityName(commodityName);
            brand.setEasyName(easyName);
            brand.setPlace(place);
            brand.setUnit(unit);
            brand.setSpec(spec);
            brand.setPack(pack);
            brand.setLotNumber(lotNumber);
            brand.setApprovalNumber(approvalNumber);
            brand.setSupplier(supplier);
            brand.setRemark(remark);

            brandDao.save(brand);

            // 数据返回
            response_data.put("message", "商品信息增加成功！");
            response_data.put("res", true);

        } catch (MyException e) {
            log.error("addBrand:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        } catch (Exception e) {
            log.error("addBrand:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 删除商品信息
     */
    @Override
    public JSONObject deleteBrand(JSONObject req) {
        log.info("Service 调用删除商品信息接口");
        JSONObject response_data = new JSONObject();
        Brand brand = new Brand();

        // 获取前端信息
        String commodityName = req.getString("commodityName"); // 商品名称

        try {
            // 校验商品全称
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(commodityName, "commodityName");

            // 查找此商品是否存在
            brand = brandDao.findByCommodityNameAndDelFlag(commodityName, true);
            if (null == brand) {
                throw new MyException(ExceptionType.AlreadyExist, "此商品信息不存在或已被删除，删除商品信息操作失败");
            }

            // 删除该商品
            brand.setDelFlag(false);
            brandDao.save(brand);

            // 数据返回
            response_data.put("message", "商品信息删除成功！");
            response_data.put("res", true);

        } catch (MyException e) {
            log.error("deleteBrand:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        } catch (Exception e) {
            log.error("deleteBrand:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 查找所有的商品信息
     */
    @Override
    public JSONObject findAllBrand() {
        log.info("Service 调用查找所有的商品信息接口");
        JSONObject response_data = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        List<Brand> brandArr = new ArrayList<>();

        try {
            brandArr = brandDao.findByDelFlag(true);

            // 遍历所有的商品销售信息
            for (int i = 0; i < brandArr.size(); i++) {
                JSONObject data = new JSONObject();
                data.put("brandId", brandArr.get(i).getBrandId());
                data.put("commodityName", brandArr.get(i).getCommodityName());
                data.put("easyName", brandArr.get(i).getEasyName());
                data.put("place", brandArr.get(i).getPlace());
                data.put("unit", brandArr.get(i).getUnit());
                data.put("spec", brandArr.get(i).getSpec());
                data.put("pack", brandArr.get(i).getPack());
                data.put("lotNumber", brandArr.get(i).getLotNumber());
                data.put("approvalNumber", brandArr.get(i).getApprovalNumber());
                data.put("supplierName", brandArr.get(i).getSupplier().getSupplierName());
                data.put("remark", brandArr.get(i).getRemark());

                // 将查找到的数据添加到 josn 数组中
                jsonArr.add(data);
            }

            // 数据返回
            response_data.put("data", jsonArr);
            response_data.put("message", "所有的商品信息查找成功！");
            response_data.put("res", true);

        } catch (Exception e) {
            log.error("findAllBrand:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    @Override
    public JSONObject modifyBrand(JSONObject req) {
        log.info("Service 调用修改商品信息接口");
        JSONObject response_data = new JSONObject();
        Brand brand = new Brand();
        Supplier supplier = new Supplier();

        // 获取前端信息
        String brandId = req.getString("brandId"); // 商品 id
        String commodityName = req.getString("commodityName"); // 商品名称
        String easyName = req.getString("easyName"); // 简称
        String place = req.getString("place"); // 产地
        String unit = req.getString("unit"); // 单位
        String spec = req.getString("spec"); // 规格
        String pack = req.getString("pack"); // 包装
        String lotNumber = req.getString("lotNumber"); // 批号
        String approvalNumber = req.getString("approvalNumber"); // 批准文号
        String supplierName = req.getString("supplierName"); // 供应商全称
        String remark = req.getString("remark"); // 备注

        try {
            // 校验商品 Id
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(brandId, "brandId");
            // 校验商品全称
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(commodityName, "commodityName");
            // 校验简称
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(easyName, "easyName");
            // 校验产地
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(place, "place");
            // 校验单位
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(unit, "unit");
            // 校验规格
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(spec, "spec");
            // 校验包装
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(pack, "pack");
            // 校验批号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(lotNumber, "lotNumber");
            // 校验批准文号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(approvalNumber, "approvalNumber");
            // 校验供应商全称
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(supplierName, "supplierName");
            // 校验备注
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(remark, "remark");

            // 查找此供应商是否已存在
            supplier = supplierDao.findBySupplierNameAndDelFlag(supplierName, true);
            if (null == supplier) {
                throw new MyException(ExceptionType.AlreadyExist, "此供应商信息不存在，修改商品信息操作失败");
            }
            // 查找此商品是否存在
            brand = brandDao.findByBrandIdAndDelFlag(brandId, true);
            if (null == brand) {
                throw new MyException(ExceptionType.AlreadyExist, "此商品信息已存在，添加商品信息操作失败");
            }

            // 为实体赋值
            brand.setCommodityName(commodityName);
            brand.setEasyName(easyName);
            brand.setPlace(place);
            brand.setUnit(unit);
            brand.setSpec(spec);
            brand.setPack(pack);
            brand.setLotNumber(lotNumber);
            brand.setApprovalNumber(approvalNumber);
            brand.setSupplier(supplier);
            brand.setRemark(remark);

            brandDao.save(brand);

            // 数据返回
            response_data.put("message", "商品信息修改成功！");
            response_data.put("res", true);

        } catch (MyException e) {
            log.error("modifyBrand:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        } catch (Exception e) {
            log.error("modifyBrand:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 获取所有的商品全称
     */
    @Override
    public JSONObject getAllCommodityName() {
        log.info("Service 调用获取所有的商品全称接口");
        JSONObject response_data = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        List<Brand> brandArr = new ArrayList<>();

        try {
            brandArr = brandDao.findByDelFlag(true);

            for (int i = 0; i < brandArr.size(); i++) {
                jsonArr.add(brandArr.get(i).getCommodityName());
            }

            // 数据返回
            response_data.put("data", jsonArr);
            response_data.put("message", "所有商品的全称查找成功！");
            response_data.put("res", true);

        } catch (Exception e) {
            log.error("findAllCommodityName:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

}
