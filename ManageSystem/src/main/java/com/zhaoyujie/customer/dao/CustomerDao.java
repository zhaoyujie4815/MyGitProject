package com.zhaoyujie.customer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zhaoyujie.customer.domain.Customer;

public interface CustomerDao extends JpaRepository<Customer, String> {
        
    /**
     * 根据客户的姓名来查找该客户的信息
     * 
     * @param customerName
     * @return
     */
    Customer findByCustomerName(String customerName);
    
    /**
     * 根据客户的姓名和该客户是否被删除来查找该客户的信息
     * 
     * @param customerName
     * @param delflag
     * @return
     */
    Customer findByCustomerNameAndDelFlag(String customerName , Boolean delflag);
    
    /**
     * 根据客户的编号和该客户是否被删除来查找该客户的信息
     * 
     * @param customerId
     * @param delflag
     * @return
     */
    Customer findByCustomerIdAndDelFlag(String customerId , Boolean delflag);
    
    /**
     * 根据客户是否被删除来查找客户的信息
     * 
     * @param delflag
     * @return
     */
    List<Customer> findByDelFlag(Boolean delflag);

}
