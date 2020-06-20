package com.zhaoyujie.stockupback.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.zhaoyujie.stock.domain.Stock;
import com.zhaoyujie.user.domain.User;

/**
 * 入库退货表
 * 
 * @author zhaoyujie
 *
 */
@Entity
public class StockUpBack implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 50)
    private String stockUpBackId; // 退货票号 将利用时间戳和随机数生成

    /**
     * 删除标识（逻辑删除 true : 未被删除 false : 已被删除）
     */
    private boolean delFlag = true;

    @ManyToOne
    @JoinColumn(name = "commodityId")
    private Stock stock; // 库存信息

    private int stockUpBackNumber; // 退货数量

    private String backTime; // 退货日期

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 操作员

    private String operator; // 经手人

    private String method; // 结算方式

    public String getStockUpBackId() {
        return stockUpBackId;
    }

    public void setStockUpBackId(String stockUpBackId) {
        this.stockUpBackId = stockUpBackId;
    }

    public boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public int getStockUpBackNumber() {
        return stockUpBackNumber;
    }

    public void setStockUpBackNumber(int stockUpBackNumber) {
        this.stockUpBackNumber = stockUpBackNumber;
    }

    public String getBackTime() {
        return backTime;
    }

    public void setBackTime(String backTime) {
        this.backTime = backTime;
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
