package com.zhaoyujie.supplier.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zhaoyujie.supplier.domain.Supplier;

public interface SupplierDao extends JpaRepository<Supplier, String> {

    /**
     * 根据供应商的全称来查找该供应商的信息
     * 
     * @param supplierName
     * @return
     */
    Supplier findBySupplierName(String supplierName);

    /**
     * 根据供应商的全称和该供应商是否被删除来查找该供应商的信息
     * 
     * @param supplierName
     * @param delflag
     * @return
     */
    Supplier findBySupplierNameAndDelFlag(String supplierName, Boolean delflag);

    /**
     * 根据供应商的编号和该供应商是否被删除来查找该供应商的信息
     * 
     * @param id
     * @param delflag
     * @return
     */
    Supplier findBySupplierIdAndDelFlag(String id, Boolean delflag);

    /**
     * 根据供应商是否被删除的标识来查找出对应状态的供应商信息
     * 
     * @param delflag
     * @return
     */
    List<Supplier> findByDelFlag(Boolean delflag);

}
