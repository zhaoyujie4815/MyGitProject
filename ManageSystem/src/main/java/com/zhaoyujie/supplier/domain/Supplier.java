package com.zhaoyujie.supplier.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 供应商信息表
 * 
 * @author zhaoyujie
 *
 */
@Entity
public class Supplier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 50)
    private String supplierId; // 供应商编号 使用时间戳和随机数生成

    /**
     * 删除标识（逻辑删除 true : 未被删除 false : 已被删除）
     */
    private boolean delFlag = true;

    @Column(name = "supplier_name", length = 100, unique = true)
    private String supplierName; // 供应商全称

    @Column(length = 20)
    private String easyName; // 简称

    @Column(length = 100)
    private String address; // 地址

    @Column(length = 50)
    private String postCode; // 邮政编码

    @Column(length = 50)
    private String phone; // 电话

    @Column(length = 50)
    private String fax; // 传真

    @Column(length = 50)
    private String contactName; // 联系人

    @Column(length = 30)
    private String contactPhone; // 联系人电话

    @Column(length = 50)
    private String email; // 电子邮箱

    @Column(length = 50)
    private String bankName; // 开户银行

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getEasyName() {
        return easyName;
    }

    public void setEasyName(String easyName) {
        this.easyName = easyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
