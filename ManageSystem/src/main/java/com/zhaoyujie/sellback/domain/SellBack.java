package com.zhaoyujie.sellback.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.zhaoyujie.sell.domain.Sell;
import com.zhaoyujie.user.domain.User;

/**
 * 销售退货表
 * 
 * @author zhaoyujie
 *
 */
@Entity
public class SellBack implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 50)
    private String sellBackId; // 退货票号

    /**
     * 删除标识（逻辑删除 true : 未被删除 false : 已被删除）
     */
    private boolean delFlag = true;

    @ManyToOne
    @JoinColumn(name = "sell_id")
    private Sell sell;

    private int sellbackNumber; // 销售退货数量

    private String backTime; // 退货日期

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 操作员

    private String operator; // 经手人

    private String method; // 结算方式

    public String getSellBackId() {
        return sellBackId;
    }

    public void setSellBackId(String sellBackId) {
        this.sellBackId = sellBackId;
    }

    public boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public Sell getSell() {
        return sell;
    }

    public void setSell(Sell sell) {
        this.sell = sell;
    }

    public int getSellbackNumber() {
        return sellbackNumber;
    }

    public void setSellbackNumber(int sellbackNumber) {
        this.sellbackNumber = sellbackNumber;
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
