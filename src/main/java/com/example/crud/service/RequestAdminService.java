package com.example.crud.service;

import com.example.crud.model.RequestAdmins;
import com.example.crud.repository.RequestAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Service
public class RequestAdminService {

    @Autowired
    private RequestAdminRepository requestAdminRepository;

    public RequestAdmins getById(long id) {
        RequestAdmins requestAdmins = null;
        Optional<RequestAdmins> optional = requestAdminRepository.findById(id);
        if(optional.isPresent()) {
            requestAdmins = optional.get();
        }
        return requestAdmins;
    }

    public List<RequestAdmins> findAll() {
        return requestAdminRepository.findAll();
    }

    public void save(RequestAdmins requestAdmins) {
        requestAdminRepository.save(requestAdmins);
    }

    public void deleteById(long requestId) {
        requestAdminRepository.deleteById(requestId);
    }
}
