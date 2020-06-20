package com.zhaoyujie.stockup.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zhaoyujie.stockup.domain.StockUp;
import com.zhaoyujie.stockup.domain.StockUpCloseAccount;

public interface StockUpCloseAccountDao extends JpaRepository<StockUpCloseAccount, String> {
    
    /**
     * 根据入库信息来查询入库结账的信息
     * 
     * @param stockUp
     * @return
     */
    List<StockUpCloseAccount> findByStockUp(StockUp stockUp);
    
    /**
     * 根据入库结账信息是否被删除来查找入库结账信息
     * 
     * @param delflag
     * @return
     */
    List<StockUpCloseAccount> findByDelFlag(Boolean delflag);
}
