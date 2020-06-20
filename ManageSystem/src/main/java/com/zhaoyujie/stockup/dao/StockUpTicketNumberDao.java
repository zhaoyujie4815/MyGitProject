package com.zhaoyujie.stockup.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zhaoyujie.stockup.domain.StockUp;
import com.zhaoyujie.stockup.domain.StockUpTicketNumber;

public interface StockUpTicketNumberDao extends JpaRepository<StockUpTicketNumber, String> {

    /**
     * 根据入库信息来查找入库票号信息
     * 
     * @param stockUp
     * @return
     */
    StockUpTicketNumber findByStockUp(StockUp stockUp);

}
