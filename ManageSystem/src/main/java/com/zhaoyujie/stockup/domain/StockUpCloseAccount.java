package com.zhaoyujie.stockup.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.zhaoyujie.user.domain.User;

/**
 * 入库结账表
 * 
 * @author zhaoyujie
 *
 */
@Entity
public class StockUpCloseAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 50)
    private String stockUpCloseAccountId; // 结款票号 将利用时间戳和随机数生成

    /**
     * 删除标识（逻辑删除 true : 未被删除 false : 已被删除）
     */
    private boolean delFlag = true;

    @ManyToOne
    @JoinColumn(name = "stockUp_id") // 外键关联入库表的入库票号
    private StockUp stockUp; // 入库表

    private double closeAccount; // 本次结款

    private String closeAccountTime; // 结款日期

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 操作员

    private String operator; // 经手人

    public String getStockUpCloseAccountId() {
        return stockUpCloseAccountId;
    }

    public void setStockUpCloseAccountId(String stockUpCloseAccountId) {
        this.stockUpCloseAccountId = stockUpCloseAccountId;
    }

    public boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public StockUp getStockUp() {
        return stockUp;
    }

    public void setStockUp(StockUp stockUp) {
        this.stockUp = stockUp;
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
