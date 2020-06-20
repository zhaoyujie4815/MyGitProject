package com.zhaoyujie.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zhaoyujie.user.domain.User;

public interface UserDao extends JpaRepository<User, Integer> {
    
    /**
     * 根据账号查询用户
     * 
     * @param account
     * @return
     */
    User findByAccount(String account);
}
