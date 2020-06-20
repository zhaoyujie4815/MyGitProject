package com.zhaoyujie.stockup.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zhaoyujie.brand.domain.Brand;
import com.zhaoyujie.stockup.domain.StockUp;

public interface StockUpDao extends JpaRepository<StockUp, String> {

    /**
     * 根据商品和入库信息是否删除来查找入库信息
     * 
     * @param brand
     * @param delflag
     * @return
     */
    List<StockUp> findByBrandAndDelFlag(Brand brand, Boolean delflag);

    /**
     * 根据入库票号和入库信息是否删除来查找入库信息
     * 
     * @param stockUpId
     * @param delflag
     * @return
     */
    StockUp findByStockUpIdAndDelFlag(String stockUpId, Boolean delflag);

    /**
     * 根据入库信息是否删除来查找入库信息
     * 
     * @param delflag
     * @return
     */
    List<StockUp> findByDelFlag(Boolean delflag);

}
