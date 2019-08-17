package com.noodleesystem.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.noodleesystem.gateway.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{ }
