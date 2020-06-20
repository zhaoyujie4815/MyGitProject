package com.zhaoyujie.sell.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zhaoyujie.sell.domain.Sell;
import com.zhaoyujie.stock.domain.Stock;

public interface SellDao extends JpaRepository<Sell, String> {

    /**
     * 根据库存信息来查找商品销售信息
     * 
     * @param stock
     * @return
     */
    List<Sell> findByStock(Stock stock);

    /**
     * 根据库存信息和商品销售信息是否被删除来查找商品销售信息
     * 
     * @param stock
     * @param delflag
     * @return
     */
    List<Sell> findByStockAndDelFlag(Stock stock, Boolean delflag);

    /**
     * 根据销售票号和商品销售信息是否被删除来查找商品销售信息
     * 
     * @param sellId
     * @param delflag
     * @return
     */
    Sell findBySellIdAndDelFlag(String sellId, Boolean delflag);

    /**
     * 根据商品销售信息是否被删除来查找商品销售信息
     * 
     * @param delflag
     * @return
     */
    List<Sell> findByDelFlag(Boolean delflag);
}
