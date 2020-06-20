package com.zhaoyujie.sellback.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zhaoyujie.sell.domain.Sell;
import com.zhaoyujie.sellback.domain.SellBack;

public interface SellBackDao extends JpaRepository<SellBack, String> {
    
    /**
     * 根据销售信息来查找销售退货的信息
     * 
     * @param sell
     * @return
     */
    List<SellBack> findBySell(Sell sell);
    
    /**
     * 根据销售退货票号和商品销售退回信息是否被删除来查找商品销售退回信息
     * 
     * @param sellBackId
     * @param delflag
     * @return
     */
    SellBack findBySellBackIdAndDelFlag(String sellBackId, Boolean delflag);
    
    /**
     * 根据商品销售退回信息是否被删除来查找商品销售退回信息
     * 
     * @param delflag
     * @return
     */
    List<SellBack> findByDelFlag(Boolean delflag);

}
