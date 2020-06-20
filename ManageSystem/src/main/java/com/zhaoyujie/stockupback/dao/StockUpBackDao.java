package com.zhaoyujie.stockupback.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zhaoyujie.stock.domain.Stock;
import com.zhaoyujie.stockupback.domain.StockUpBack;

public interface StockUpBackDao extends JpaRepository<StockUpBack, String> {

   /**
    * 根据库存信息和入库退货信息是否被删除来查找入库退货信息
    * 
    * @param stock
    * @param delflag
    * @return
    */
    List<StockUpBack> findByStockAndDelFlag(Stock stock, Boolean delflag);

    /**
     * 根据入库的票号和入库退货信息是否删除来查找入库退货的信息
     * 
     * @param stockUpBackId
     * @param delflag
     * @return
     */
    StockUpBack findByStockUpBackIdAndDelFlag(String stockUpBackId, Boolean delflag);

    /**
     * 根据入库退货信息是否删除来查找入库退货的信息
     * 
     * @param delflag
     * @return
     */
    List<StockUpBack> findByDelFlag(Boolean delflag);

}
