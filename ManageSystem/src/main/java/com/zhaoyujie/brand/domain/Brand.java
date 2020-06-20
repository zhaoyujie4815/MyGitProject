package com.zhaoyujie.brand.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.zhaoyujie.supplier.domain.Supplier;

/**
 * @ClassName: Brand
 * @Description: TODO
 * @author 商品信息表
 * @date 2020-06-20 20:09:00
 */
@Entity
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 50)
    private String brandId; // 主键 id 号 将利用时间戳和随机数生成

    /**
     * 删除标识（逻辑删除 true : 未被删除 false : 已被删除）
     */
    private boolean delFlag = true;

    @Column(name = "commodity_name", unique = true) // 唯一约束
    private String commodityName; // 商品名称

    @Column(length = 20)
    private String easyName; // 简称

    @Column(length = 100)
    private String place; // 产地

    @Column(length = 10)
    private String unit; // 单位

    @Column(length = 50)
    private String spec; // 规格

    @Column(length = 50)
    private String pack; // 包装

    @Column(length = 50)
    private String lotNumber; // 批号

    @Column(length = 50)
    private String approvalNumber; // 批准文号

    @ManyToOne
    @JoinColumn(name = "supplier_name", referencedColumnName = "supplier_name")
    private Supplier supplier; // 供应商

    @Column(length = 255)
    private String remark; // 备注

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getEasyName() {
        return easyName;
    }

    public void setEasyName(String easyName) {
        this.easyName = easyName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getApprovalNumber() {
        return approvalNumber;
    }

    public void setApprovalNumber(String approvalNumber) {
        this.approvalNumber = approvalNumber;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
