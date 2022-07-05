package com.example.crud.repository;

import com.example.crud.model.RequestAdmins;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestAdminRepository extends JpaRepository<RequestAdmins, Long> {
}

