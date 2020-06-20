package com.zhaoyujie.sellback.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 销售退货票号表
 * 
 * @author zhaoyujie
 *
 */
@Entity
public class SellBackTicketNumber implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 50)
    private String sellBackTickNumberId; // 利用时间戳和随机数生成

    @ManyToOne
    @JoinColumn(name = "sellBack_id") // 外键关联销售退货表的销售退货票号
    private SellBack sellBack; // 销售退货表

    /**
     * 删除标识（逻辑删除 true : 未被删除 false : 已被删除）
     */
    private boolean delFlag = true;

    private double payable; // 应付

    private double paid; // 实付

    private double unpaid; // 未付

    private boolean payFlag; // 是否结清

    public String getSellBackTickNumberId() {
        return sellBackTickNumberId;
    }

    public void setSellBackTickNumberId(String sellBackTickNumberId) {
        this.sellBackTickNumberId = sellBackTickNumberId;
    }

    public SellBack getSellBack() {
        return sellBack;
    }

    public void setSellBack(SellBack sellBack) {
        this.sellBack = sellBack;
    }

    public boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public double getPayable() {
        return payable;
    }

    public void setPayable(double payable) {
        this.payable = payable;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public double getUnpaid() {
        return unpaid;
    }

    public void setUnpaid(double unpaid) {
        this.unpaid = unpaid;
    }

    public boolean isPayFlag() {
        return payFlag;
    }

    public void setPayFlag(boolean payFlag) {
        this.payFlag = payFlag;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
