package com.zhaoyujie.sell.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zhaoyujie.sell.domain.Sell;
import com.zhaoyujie.sell.domain.SellTicketNumber;

public interface SellTicketNumberDao extends JpaRepository<SellTicketNumber, String> {
    
    /**
     * 根据销售信息来查找销售票号的信息
     * 
     * @param sell
     * @return
     */
    SellTicketNumber findBySell(Sell sell);
}
