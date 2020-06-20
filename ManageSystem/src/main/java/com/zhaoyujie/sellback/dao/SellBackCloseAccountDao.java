package com.zhaoyujie.sellback.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zhaoyujie.sellback.domain.SellBackCloseAccount;

public interface SellBackCloseAccountDao extends JpaRepository<SellBackCloseAccount, String> {
    
    /**
     * 根据销售退货结账信息是否存在来查找销售退货结账信息
     * 
     * @param delflag
     * @return
     */
    List<SellBackCloseAccount> findByDelFlag(Boolean delflag);
}
