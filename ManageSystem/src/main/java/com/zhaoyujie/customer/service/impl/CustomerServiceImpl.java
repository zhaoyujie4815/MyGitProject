package com.zhaoyujie.customer.service.impl;

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
import com.zhaoyujie.customer.dao.CustomerDao;
import com.zhaoyujie.customer.domain.Customer;
import com.zhaoyujie.customer.service.CustomerService;
import com.zhaoyujie.util.CheckStringIsEmptyAndNullUtil;
import com.zhaoyujie.util.ExceptionType;
import com.zhaoyujie.util.MyException;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    CustomerDao customerDao;

    Random random = new Random();

    /**
     * Service 增加客户信息
     */
    @Override
    public JSONObject addCustomer(JSONObject req) {
        log.info("Service 调用增加客户信息接口");
        JSONObject response_data = new JSONObject();
        Customer customer = new Customer();

        // 获取前端信息
        String customerName = req.getString("customerName"); // 客户全称
        String easyName = req.getString("easyName"); // 简称
        String address = req.getString("address"); // 地址
        String postCode = req.getString("postCode"); // 邮政编码
        String phone = req.getString("phone"); // 电话
        String fax = req.getString("fax"); // 传真
        String contactName = req.getString("contactName"); // 联系人
        String contactPhone = req.getString("contactPhone"); // 联系人电话
        String email = req.getString("email"); // 电子邮箱
        String bankName = req.getString("bankName"); // 开户银行
        String bankNumber = req.getString("bankNumber"); // 银行账号

        try {
            // 校验客户全称
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(customerName,"customerName");
            // 校验简称
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(easyName,"easyName");
            // 校验地址
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(address,"address");
            // 校验邮政编码
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(postCode,"postCode");
            // 校验电话
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(phone,"phone");
            // 校验传真
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(fax,"fax");
            // 校验联系人
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(contactName,"contactName");
            // 校验联系人电话
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(contactPhone,"contactPhone");
            // 校验电子邮箱
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(email,"email");
            // 校验开户银行
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(bankName,"bankName");
            // 校验银行账号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(bankNumber,"bankNumber");

            // 查找此客户是否已存在
            if (null != customerDao.findByCustomerName(customerName)) {
                throw new MyException(ExceptionType.AlreadyExist, "此客户信息已存在，添加失败");
            }

            // 为实体赋值
            customer.setCustomerId("" + System.currentTimeMillis() + random.nextInt(10));
            customer.setCustomerName(customerName);
            customer.setEasyName(easyName);
            customer.setAddress(address);
            customer.setPostCode(postCode);
            customer.setPhone(phone);
            customer.setFax(fax);
            customer.setContactName(contactName);
            customer.setContactPhone(contactPhone);
            customer.setEmail(email);
            customer.setBankName(bankName);
            customer.setBankNumber(bankNumber);

            customerDao.save(customer);

            // 数据返回
            response_data.put("message", "客户信息增加成功！");
            response_data.put("res", true);

        } catch (MyException e) {
            log.error("addCustomer:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        } catch (Exception e) {
            log.error("addCustomer:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 删除客户信息
     */
    @Override
    public JSONObject deleteCustomer(JSONObject req) {
        log.info("Service 调用删除客户信息接口");
        JSONObject response_data = new JSONObject();
        Customer customer = new Customer();

        // 获取前端信息
        String customerName = req.getString("customerName"); // 客户全称

        try {
            // 校验供应商全称
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(customerName,"customerName");

            // 查找此供应商是否存在
            customer = customerDao.findByCustomerNameAndDelFlag(customerName, true);
            if (null == customer) {
                throw new MyException(ExceptionType.NotFoundField, "此客户信息不存在或已被删除，无法进行删除操作");
            }

            // 删除改供应商信息
            customer.setDelFlag(false);
            customerDao.save(customer);

            // 数据返回
            response_data.put("message", "客户信息删除成功！");
            response_data.put("res", true);

        } catch (MyException e) {
            log.error("deleteCustomer:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        } catch (Exception e) {
            log.error("deleteCustomer:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 查找客户信息
     */
    @Override
    public JSONObject findCustomer(JSONObject req) {
        log.info("Service 调用查找客户信息接口");
        JSONObject response_data = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        JSONObject data = new JSONObject();
        Customer customer = new Customer();

        // 获取前端信息
        String customerName = req.getString("customerName"); // 客户全称

        try {
            // 校验供应商全称
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(customerName,"customerName");

            // 查找此供应商是否存在
            customer = customerDao.findByCustomerNameAndDelFlag(customerName, true);
            if (null == customer) {
                throw new MyException(ExceptionType.NotFoundField, "此客户信息不存在，查找失败");
            }

            data.put("customerId", customer.getCustomerId());
            data.put("customerName", customer.getCustomerName());
            data.put("easyName", customer.getEasyName());
            data.put("address", customer.getAddress());
            data.put("postCode", customer.getPostCode());
            data.put("phone", customer.getPhone());
            data.put("fax", customer.getFax());
            data.put("contactName", customer.getContactName());
            data.put("contactPhone", customer.getContactPhone());
            data.put("email", customer.getEmail());
            data.put("bankName", customer.getBankName());
            data.put("bankNumber", customer.getBankNumber());

            // 将查找到的数据添加到 josn 数组中
            jsonArr.add(data);

            // 数据返回
            response_data.put("data", jsonArr);
            response_data.put("message", "客户信息查找成功！");
            response_data.put("res", true);

        } catch (MyException e) {
            log.error("findCustomer:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        } catch (Exception e) {
            log.error("findCustomer:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 查找所有的供应商信息接口
     */
    @Override
    public JSONObject findAllCustomer() {
        log.info("Service 调用查找所有的客户信息接口");
        JSONObject response_data = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        List<Customer> customerArr = new ArrayList<>();

        try {
            customerArr = customerDao.findByDelFlag(true);

         // 遍历所有的供应商信息
            for (int i = 0; i < customerArr.size(); i++) {
                JSONObject data = new JSONObject();
                data.put("customerId", customerArr.get(i).getCustomerId());
                data.put("customerName", customerArr.get(i).getCustomerName());
                data.put("easyName", customerArr.get(i).getEasyName());
                data.put("address", customerArr.get(i).getAddress());
                data.put("postCode", customerArr.get(i).getPostCode());
                data.put("phone", customerArr.get(i).getPhone());
                data.put("fax", customerArr.get(i).getFax());
                data.put("contactName", customerArr.get(i).getContactName());
                data.put("contactPhone", customerArr.get(i).getContactPhone());
                data.put("email", customerArr.get(i).getEmail());
                data.put("bankName", customerArr.get(i).getBankName());
                data.put("bankNumber", customerArr.get(i).getBankNumber());

                // 将查找到的数据添加到 josn 数组中
                jsonArr.add(data);
            }

            // 数据返回
            response_data.put("data", jsonArr);
            response_data.put("message", "所有的客户信息查找成功！");
            response_data.put("res", true);

        } catch (Exception e) {
            log.error("findAllCustomer:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

    /**
     * Service 修改客户信息
     */
    @Override
    public JSONObject modifyCustomer(JSONObject req) {
        log.info("Service 调用修改供应商信息接口");
        JSONObject response_data = new JSONObject();
        Customer customer = new Customer();

        // 获取前端信息
        String customerId = req.getString("customerId"); // 客户编号
        String customerName = req.getString("customerName"); // 客户全称
        String easyName = req.getString("easyName"); // 简称
        String address = req.getString("address"); // 地址
        String postCode = req.getString("postCode"); // 邮政编码
        String phone = req.getString("phone"); // 电话
        String fax = req.getString("fax"); // 传真
        String contactName = req.getString("contactName"); // 联系人
        String contactPhone = req.getString("contactPhone"); // 联系人电话
        String email = req.getString("email"); // 电子邮箱
        String bankName = req.getString("bankName"); // 开户银行
        String bankNumber = req.getString("bankNumber"); // 银行账号

        try {
            // 校验客户编号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(customerId,"customerId");
         // 校验客户全称
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(customerName,"customerName");
            // 校验简称
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(easyName,"easyName");
            // 校验地址
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(address,"address");
            // 校验邮政编码
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(postCode,"postCode");
            // 校验电话
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(phone,"phone");
            // 校验传真
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(fax,"fax");
            // 校验联系人
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(contactName,"contactName");
            // 校验联系人电话
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(contactPhone,"contactPhone");
            // 校验电子邮箱
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(email,"email");
            // 校验开户银行
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(bankName,"bankName");
            // 校验银行账号
            CheckStringIsEmptyAndNullUtil.checkStringIsEmptyAndNull(bankNumber,"bankNumber");

            // 查找此供应商是否已存在
            customer = customerDao.findByCustomerIdAndDelFlag(customerId, true);
            if (null == customer) {
                throw new MyException(ExceptionType.NotFound, "此客户信息不存在，无法修改");
            }

            // 为实体赋值
            customer.setCustomerName(customerName);
            customer.setEasyName(easyName);
            customer.setAddress(address);
            customer.setPostCode(postCode);
            customer.setPhone(phone);
            customer.setFax(fax);
            customer.setContactName(contactName);
            customer.setContactPhone(contactPhone);
            customer.setEmail(email);
            customer.setBankName(bankName);
            customer.setBankNumber(bankNumber);

            customerDao.save(customer);

            // 数据返回
            response_data.put("message", "客户信息修改成功！");
            response_data.put("res", true);

        } catch (MyException e) {
            log.error("modifyCustomer:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        } catch (Exception e) {
            log.error("modifyCustomer:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }
    
    /**
     * Service 获取所有的客户全称
     */
    @Override
    public JSONObject getAllCustomerName() {
        log.info("Service 调用获取所有的客户全称接口");
        JSONObject response_data = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        List<Customer> customerArr = new ArrayList<>();

        try {
            customerArr = customerDao.findByDelFlag(true);

            for (int i = 0; i < customerArr.size(); i++) {
                jsonArr.add(customerArr.get(i).getCustomerName());
            }

            // 数据返回
            response_data.put("data", jsonArr);
            response_data.put("message", "所有客户的全称查找成功！");
            response_data.put("res", true);

        } catch (Exception e) {
            log.error("getAllCustomerName:" + e.getMessage());
            response_data.put("res", false);
            response_data.put("exception", e.getMessage());
        }

        return response_data;
    }

}
