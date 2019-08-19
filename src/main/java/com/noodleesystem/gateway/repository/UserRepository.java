package com.noodleesystem.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.noodleesystem.gateway.model.UserRegistrationModel;

@Repository
public interface UserRepository extends JpaRepository<UserRegistrationModel, Long>{
    @Async
    @Query("SELECT u FROM UserApiModel u WHERE u.username = :username")
    UserRegistrationModel findByUsername(@Param("username") final String username);
}
