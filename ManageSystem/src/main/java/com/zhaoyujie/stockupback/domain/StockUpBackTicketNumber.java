package com.zhaoyujie.stockupback.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 入库退货票号表
 * 
 * @author zhaoyujie
 *
 */
@Entity
public class StockUpBackTicketNumber implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 50)
    private String stockUpBackTicketNumberId; // 利用时间戳和随机数生成

    @ManyToOne
    @JoinColumn(name = "stockUpBack_id") // 外键关联入库退货表的入库票号
    private StockUpBack stockUpBack; // 入库退货表

    /**
     * 删除标识（逻辑删除 true : 未被删除 false : 已被删除）
     */
    private boolean delFlag = true;

    private double receivable; // 应收

    private double receipt; // 实收

    private double uncollected; // 未收

    private boolean payFlag; // 是否结清

    public String getStockUpBackTicketNumberId() {
        return stockUpBackTicketNumberId;
    }

    public void setStockUpBackTicketNumberId(String stockUpBackTicketNumberId) {
        this.stockUpBackTicketNumberId = stockUpBackTicketNumberId;
    }

    public StockUpBack getStockUpBack() {
        return stockUpBack;
    }

    public void setStockUpBack(StockUpBack stockUpBack) {
        this.stockUpBack = stockUpBack;
    }

    public boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public double getReceivable() {
        return receivable;
    }

    public void setReceivable(double receivable) {
        this.receivable = receivable;
    }

    public double getReceipt() {
        return receipt;
    }

    public void setReceipt(double receipt) {
        this.receipt = receipt;
    }

    public double getUncollected() {
        return uncollected;
    }

    public void setUncollected(double uncollected) {
        this.uncollected = uncollected;
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
