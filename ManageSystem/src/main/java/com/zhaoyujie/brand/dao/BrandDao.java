package com.zhaoyujie.brand.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zhaoyujie.brand.domain.Brand;

public interface BrandDao extends JpaRepository<Brand, String> {
    
    /**
     * 根据商品名称来查找该商品信息
     * 
     * @param commodityName
     * @return
     */
    Brand findByCommodityName(String commodityName);
    
    /**
     * 根据商品名称和该商品是否被删除来查找该商品的信息
     * 
     * @param commodityName
     * @param delflag
     * @return
     */
    Brand findByCommodityNameAndDelFlag(String commodityName,Boolean delflag);
    
    /**
     * 根据商品编号和该商品是否被删除来查找该商品的信息
     * 
     * @param brandId
     * @param delflag
     * @return
     */
    Brand findByBrandIdAndDelFlag(String brandId , Boolean delflag);
    
    /**
     * 根据商品是否被删除的标识来查找出对应状态的商品信息
     * 
     * @param delflag
     * @return
     */
    List<Brand> findByDelFlag(Boolean delflag);

}
