package com.spider.ctcontrol.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spider.ctcontrol.entities.User;

public interface UserRepository extends JpaRepository<User, String> {
    
    Optional<User> findByUsername(String username);
}
