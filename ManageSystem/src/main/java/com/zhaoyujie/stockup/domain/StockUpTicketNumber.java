package com.zhaoyujie.stockup.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 入库票号表
 * 
 * @author zhaoyujie
 *
 */
@Entity
public class StockUpTicketNumber implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(length = 50)
    private String stockUpTicketNumberId; // 利用时间戳和随机数生成

    @ManyToOne
    @JoinColumn(name = "stockUp_id",unique = true) // 外键关联入库表的入库票号
    private StockUp stockUp; // 入库表

    /**
     * 删除标识（逻辑删除 true : 未被删除 false : 已被删除）
     */
    private boolean delFlag = true;

    private double payable; // 应付

    private double paid; // 实付

    private double unpaid; // 未付

    private boolean payFlag; // 是否结清

    public String getStockUpTicketNumberId() {
        return stockUpTicketNumberId;
    }

    public void setStockUpTicketNumberId(String stockUpTicketNumberId) {
        this.stockUpTicketNumberId = stockUpTicketNumberId;
    }

    public StockUp getStockUp() {
        return stockUp;
    }

    public void setStockUp(StockUp stockUp) {
        this.stockUp = stockUp;
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
