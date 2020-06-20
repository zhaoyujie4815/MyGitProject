package com.zhaoyujie.supplier.service.impl;

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
import com.zhaoyujie.supplier.dao.SupplierDao;
import com.zhaoyujie.supplier.domain.Supplier;
import com.zhaoyujie.supplier.service.SupplierService;
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
public class SupplierServiceImpl implements SupplierService {

    private static final Logger log = LoggerFactory.getLogger(SupplierServiceImpl.class);

    @Autowired
    SupplierDao supplierDao;

    Random random = new Random();

    /**
     * Service 增加供应商信息
     */
    @Override
    public JSONObject addSupplier(JSONObject req) {
        log.info("Service 调用增加供应商信息接口");
        JSONObject response_data = new JSONObject();
        Supplier supplier = new Supplier();

        // 获取前端信息
        String supplierName = req.getString("supplierName"); // 供应商全称
        String easyName = req.getString("easyName"); // 简称
        String address = req.getString("address"); // 地址
        String postCode = req.getString("postCode"); // 邮政编码
        String phone = req.getString("phone"); // 电话
        String fax = req.getString("fax"); // 传真
        String contactName = req.getString("contactName"); // 联系人
        String contactPhone = req.getString("contactPhone"); // 联系人电话
        String email = req.getString("email"); // 电子邮箱
        String bankName = req.getString("bankName"); // 开户银行

        try {
            // 校验供应商全称
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(supplierName, "supplierName");
            // 校验简称
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(easyName, "easyName");
            // 校验地址
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(address, "address");
            // 校验邮政编码
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(postCode, "postCode");
            // 校验电话
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(phone, "phone");
            // 校验传真
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(fax, "fax");
            // 校验联系人
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(contactName, "contactName");
            // 校验联系人电话
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(contactPhone, "contactPhone");
            // 校验电子邮箱
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(email, "email");
            // 校验开户银行
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(bankName, "bankName");

            // 查找此供应商是否已存在
            if (null != supplierDao.findBySupplierName(supplierName)) {
                throw new MyException(ExceptionType.AlreadyExist, "此供应商信息已存在，添加失败");
            }

            // 为实体赋值
            supplier.setSupplierId("" + System.currentTimeMillis() + random.nextInt(10));
            supplier.setSupplierName(supplierName);
            supplier.setEasyName(easyName);
            supplier.setAddress(address);
            supplier.setPostCode(postCode);
            supplier.setPhone(phone);
            supplier.setFax(fax);
            supplier.setContactName(contactName);
            supplier.setContactPhone(contactPhone);
            supplier.setEmail(email);
            supplier.setBankName(bankName);

            supplierDao.save(supplier);

            // 数据返回
            response_data.put("message", "供应商信息增加成功！");
            response_data.put("res", true);

        } catch (MyException e) {
            log.error("addSupplier:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        } catch (Exception e) {
            log.error("addSupplier:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 删除供应商信息
     */
    @Override
    public JSONObject deleteSupplier(JSONObject req) {
        log.info("Service 调用删除供应商信息接口");
        JSONObject response_data = new JSONObject();
        Supplier supplier = new Supplier();

        // 获取前端信息
        String supplierName = req.getString("supplierName"); // 供应商全称

        try {
            // 校验供应商全称
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(supplierName, "supplierName");

            // 查找此供应商是否存在
            supplier = supplierDao.findBySupplierNameAndDelFlag(supplierName, true);
            if (null == supplier) {
                throw new MyException(ExceptionType.NotFoundField, "此供应商信息不存在或已被删除，无法进行删除操作");
            }

            // 删除该供应商信息
            supplier.setDelFlag(false);
            supplierDao.save(supplier);

            // 数据返回
            response_data.put("message", "供应商信息删除成功！");
            response_data.put("res", true);

        } catch (MyException e) {
            log.error("deleteSupplier:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        } catch (Exception e) {
            log.error("deleteSupplier:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 查找供应商信息
     */
    @Override
    public JSONObject findSupplier(JSONObject req) {
        log.info("Service 调用查找供应商信息接口");
        JSONObject response_data = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        Supplier supplier = new Supplier();

        // 获取前端信息
        String supplierName = req.getString("supplierName"); // 供应商全称

        try {
            // 校验供应商全称
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(supplierName, "supplierName");

            // 查找此供应商是否存在
            supplier = supplierDao.findBySupplierNameAndDelFlag(supplierName, true);
            if (null == supplier) {
                throw new MyException(ExceptionType.NotFoundField, "此供应商信息不存在，查找失败");
            }

            data.put("supplierId", supplier.getSupplierId());
            data.put("supplierName", supplier.getSupplierName());
            data.put("easyName", supplier.getEasyName());
            data.put("address", supplier.getAddress());
            data.put("postCode", supplier.getPostCode());
            data.put("phone", supplier.getPhone());
            data.put("fax", supplier.getFax());
            data.put("contactName", supplier.getContactName());
            data.put("contactPhone", supplier.getContactPhone());
            data.put("email", supplier.getEmail());
            data.put("bankName", supplier.getBankName());

            // 将查找到的数据添加到 josn 数组中
            jsonArr.add(data);

            // 数据返回
            response_data.put("data", jsonArr);
            response_data.put("message", "供应商信息查找成功！");
            response_data.put("res", true);

        } catch (MyException e) {
            log.error("findSupplier:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        } catch (Exception e) {
            log.error("findSupplier:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 查找所有的供应商信息接口
     */
    @Override
    public JSONObject findAllSupplier() {
        log.info("Service 调用查找所有的供应商信息接口");
        JSONObject response_data = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        List<Supplier> supplierArr = new ArrayList<>();

        try {
            supplierArr = supplierDao.findByDelFlag(true);

            // 遍历所有的供应商信息
            for (int i = 0; i < supplierArr.size(); i++) {
                JSONObject data = new JSONObject();
                data.put("supplierId", supplierArr.get(i).getSupplierId());
                data.put("supplierName", supplierArr.get(i).getSupplierName());
                data.put("easyName", supplierArr.get(i).getEasyName());
                data.put("address", supplierArr.get(i).getAddress());
                data.put("postCode", supplierArr.get(i).getPostCode());
                data.put("phone", supplierArr.get(i).getPhone());
                data.put("fax", supplierArr.get(i).getFax());
                data.put("contactName", supplierArr.get(i).getContactName());
                data.put("contactPhone", supplierArr.get(i).getContactPhone());
                data.put("email", supplierArr.get(i).getEmail());
                data.put("bankName", supplierArr.get(i).getBankName());

                // 将查找到的数据添加到 josn 数组中
                jsonArr.add(data);
            }

            // 数据返回
            response_data.put("data", jsonArr);
            response_data.put("message", "所有的供应商信息查找成功！");
            response_data.put("res", true);

        } catch (Exception e) {
            log.error("findAllSupplier:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 修改供应商信息
     */
    @Override
    public JSONObject modifySupplier(JSONObject req) {
        log.info("Service 调用修改供应商信息接口");
        JSONObject response_data = new JSONObject();
        Supplier supplier = new Supplier();

        // 获取前端信息
        String supplierId = req.getString("supplierId"); // 供应商编号
        String supplierName = req.getString("supplierName"); // 供应商全称
        String easyName = req.getString("easyName"); // 简称
        String address = req.getString("address"); // 地址
        String postCode = req.getString("postCode"); // 邮政编码
        String phone = req.getString("phone"); // 电话
        String fax = req.getString("fax"); // 传真
        String contactName = req.getString("contactName"); // 联系人
        String contactPhone = req.getString("contactPhone"); // 联系人电话
        String email = req.getString("email"); // 电子邮箱
        String bankName = req.getString("bankName"); // 开户银行

        try {
            // 校验供应商编号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(supplierId, "supplierId");
            // 校验供应商全称
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(supplierName, "supplierName");
            // 校验简称
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(easyName, "easyName");
            // 校验地址
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(address, "address");
            // 校验邮政编码
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(postCode, "postCode");
            // 校验电话
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(phone, "phone");
            // 校验传真
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(fax, "fax");
            // 校验联系人
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(contactName, "contactName");
            // 校验联系人电话
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(contactPhone, "contactPhone");
            // 校验电子邮箱
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(email, "email");
            // 校验开户银行
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(bankName, "bankName");

            // 查找此供应商是否已存在
            supplier = supplierDao.findBySupplierIdAndDelFlag(supplierId, true);
            if (null == supplier) {
                throw new MyException(ExceptionType.NotFound, "此供应商信息不存在，无法修改");
            }

            // 为实体赋值
            supplier.setSupplierName(supplierName);
            supplier.setEasyName(easyName);
            supplier.setAddress(address);
            supplier.setPostCode(postCode);
            supplier.setPhone(phone);
            supplier.setFax(fax);
            supplier.setContactName(contactName);
            supplier.setContactPhone(contactPhone);
            supplier.setEmail(email);
            supplier.setBankName(bankName);

            supplierDao.save(supplier);

            // 数据返回
            response_data.put("message", "供应商信息修改成功！");
            response_data.put("res", true);

        } catch (MyException e) {
            log.error("modifySupplier:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        } catch (Exception e) {
            log.error("modifySupplier:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 获取所有供应商的全称
     */
    @Override
    public JSONObject getAllSupplierName() {
        log.info("Service 调用获取所有供应商的全称接口");
        JSONObject response_data = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        List<Supplier> supplierArr = new ArrayList<>();

        try {
            supplierArr = supplierDao.findByDelFlag(true);

            for (int i = 0; i < supplierArr.size(); i++) {
                jsonArr.add(supplierArr.get(i).getSupplierName());
            }

            // 数据返回
            response_data.put("data", jsonArr);
            response_data.put("message", "所有供应商的全称查找成功！");
            response_data.put("res", true);

        } catch (Exception e) {
            log.error("getAllSupplierName:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

}
