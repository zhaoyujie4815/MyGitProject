package com.zhaoyujie.stockupback.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zhaoyujie.stockupback.domain.StockUpBack;
import com.zhaoyujie.stockupback.domain.StockUpBackTicketNumber;

public interface StockUpBackTicketNumberDao extends JpaRepository<StockUpBackTicketNumber, String> {

    /**
     * 根据入库商品退货票号信息是否存在来查找入库商品退货票号信息
     * 
     * @param stockUpBack
     * @return
     */
    StockUpBackTicketNumber findByStockUpBack(StockUpBack stockUpBack);
}
