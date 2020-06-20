package com.zhaoyujie.stock.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zhaoyujie.brand.domain.Brand;
import com.zhaoyujie.stock.domain.Stock;

public interface StockDao extends JpaRepository<Stock, String > {
    
    /**
     * 根据商品和库存信息是否被删除来查找库存信息
     * 
     * @param brand
     * @param delflag
     * @return
     */
    List<Stock> findByBrandAndDelFlag(Brand brand , Boolean delflag);
    
    /**
     * 根据商品的编号来查找库存信息
     * 
     * @param commodityId
     * @return
     */
    Stock findByCommodityId(String commodityId);
    
    /**
     * 根据商品的编号和库存信息是否被删除来查找库存信息
     * 
     * @param commodityId
     * @param delflag
     * @return
     */
    Stock findByCommodityIdAndDelFlag(String commodityId , Boolean delflag);
    
    /**
     * 根据库存信息是否被删除来查找库存信息
     * 
     * @param delflag
     * @return
     */
    List<Stock> findByDelFlag(Boolean delflag);
}
