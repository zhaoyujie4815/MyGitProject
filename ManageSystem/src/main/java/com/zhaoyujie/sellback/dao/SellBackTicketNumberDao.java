package com.zhaoyujie.sellback.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zhaoyujie.sellback.domain.SellBack;
import com.zhaoyujie.sellback.domain.SellBackTicketNumber;

public interface SellBackTicketNumberDao extends JpaRepository<SellBackTicketNumber, String> {

    SellBackTicketNumber findBySellBack(SellBack sellBack);
}
