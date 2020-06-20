package com.zhaoyujie.stockupback.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.zhaoyujie.user.domain.User;

/**
 * 入库退货结账表
 * 
 * @author zhaoyujie
 *
 */
@Entity
public class StockUpBackCloseAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 50)
    private String stockUpBackCloseAccountId; // 结款票号 将利用时间戳和随机数生成

    /**
     * 删除标识（逻辑删除 true : 未被删除 false : 已被删除）
     */
    private boolean delFlag = true;

    @ManyToOne
    @JoinColumn(name = "stockUpBack_id") // 外键关联入库退货表的入库票号
    private StockUpBack stockUpBack; // 入库退货表

    private double closeAccount; // 本次结款

    private String closeAccountTime; // 结款日期

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 操作员

    private String operator; // 经手人

    public String getStockUpBackCloseAccountId() {
        return stockUpBackCloseAccountId;
    }

    public void setStockUpBackCloseAccountId(String stockUpBackCloseAccountId) {
        this.stockUpBackCloseAccountId = stockUpBackCloseAccountId;
    }

    public boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public StockUpBack getStockUpBack() {
        return stockUpBack;
    }

    public void setStockUpBack(StockUpBack stockUpBack) {
        this.stockUpBack = stockUpBack;
    }

    public double getCloseAccount() {
        return closeAccount;
    }

    public void setCloseAccount(double closeAccount) {
        this.closeAccount = closeAccount;
    }

    public String getCloseAccountTime() {
        return closeAccountTime;
    }

    public void setCloseAccountTime(String closeAccountTime) {
        this.closeAccountTime = closeAccountTime;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
