package com.example.crud.repository;

import com.example.crud.model.RequestAdmins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestAdminRepository extends JpaRepository<RequestAdmins, Long> {
}

