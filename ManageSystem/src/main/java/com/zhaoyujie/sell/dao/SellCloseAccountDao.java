package com.zhaoyujie.sell.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zhaoyujie.sell.domain.SellCloseAccount;

public interface SellCloseAccountDao extends JpaRepository<SellCloseAccount, String> {
    
    /**
     * 根据销售结账信息是否存在来查找销售结账信息
     * 
     * @param delflag
     * @return
     */
    List<SellCloseAccount> findByDelFlag(Boolean delflag);

}
