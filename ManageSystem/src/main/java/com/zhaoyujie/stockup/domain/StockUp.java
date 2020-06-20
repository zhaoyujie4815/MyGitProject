package com.zhaoyujie.stockup.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.zhaoyujie.brand.domain.Brand;
import com.zhaoyujie.user.domain.User;

/**
 * 入库表
 * 
 * @author zhaoyujie
 *
 */
@Entity
public class StockUp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 50)
    private String stockUpId; // 入库票号 将利用时间戳和随机数生成

    /**
     * 删除标识（逻辑删除 true : 未被删除 false : 已被删除）
     */
    private boolean delFlag = true;

    @ManyToOne
    @JoinColumn(name = "brand_name", referencedColumnName = "commodity_name")
    private Brand brand; // 商品

    private int stockUpNumber; // 入库数量

    private double price; // 单价

    private String stockUpTime; // 入库日期

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 操作员

    private String operator; // 经手人

    private String method; // 结算方式

    public String getStockUpId() {
        return stockUpId;
    }

    public void setStockUpId(String stockUpId) {
        this.stockUpId = stockUpId;
    }

    public boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public int getStockUpNumber() {
        return stockUpNumber;
    }

    public void setStockUpNumber(int stockUpNumber) {
        this.stockUpNumber = stockUpNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStockUpTime() {
        return stockUpTime;
    }

    public void setStockUpTime(String stockUpTime) {
        this.stockUpTime = stockUpTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
