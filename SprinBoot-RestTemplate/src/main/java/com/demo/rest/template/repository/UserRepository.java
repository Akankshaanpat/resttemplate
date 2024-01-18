package com.demo.rest.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.rest.template.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
