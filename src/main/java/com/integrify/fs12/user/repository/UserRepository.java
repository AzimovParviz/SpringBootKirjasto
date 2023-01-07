package com.integrify.fs12.user.repository;

import com.integrify.fs12.user.bean.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
