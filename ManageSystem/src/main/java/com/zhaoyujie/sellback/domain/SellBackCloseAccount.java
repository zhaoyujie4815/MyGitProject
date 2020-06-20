package com.zhaoyujie.sellback.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.zhaoyujie.user.domain.User;

/**
 * 销售退货结账表
 * 
 * @author zhaoyujie
 *
 */
@Entity
public class SellBackCloseAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 50)
    private String sellBackCloseAccountId; // 结款票号 将利用时间戳和随机数生成

    /**
     * 删除标识（逻辑删除 true : 未被删除 false : 已被删除）
     */
    private boolean delFlag = true;

    @ManyToOne
    @JoinColumn(name = "sellBack_id") // 外键关联销售退货表的销售退货票号
    private SellBack SellBack; // 销售退货表

    private double closeAccount; // 本次结款

    private String closeAccountTime; // 结款日期

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 操作员

    private String operator; // 经手人

    public String getSellBackCloseAccountId() {
        return sellBackCloseAccountId;
    }

    public void setSellBackCloseAccountId(String sellBackCloseAccountId) {
        this.sellBackCloseAccountId = sellBackCloseAccountId;
    }

    public boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public SellBack getSellBack() {
        return SellBack;
    }

    public void setSellBack(SellBack sellBack) {
        SellBack = sellBack;
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
