package com.zhaoyujie.stockupback.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zhaoyujie.stockupback.domain.StockUpBackCloseAccount;

public interface StockUpBackCloseAccountDao extends JpaRepository<StockUpBackCloseAccount, String> {
    
    /**
     * 根据信息是否被删除来查找入库退货结账的信息
     * 
     * @param delflag
     * @return
     */
    List<StockUpBackCloseAccount> findByDelFlag(Boolean delflag);
    
}
