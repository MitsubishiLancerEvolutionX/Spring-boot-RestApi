package com.example.crud.repository;

import com.example.crud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository <User, Long> {
    @Query("select u from User u join fetch u.roles where u.login = :login")
    User findByLogin(String login);
}

