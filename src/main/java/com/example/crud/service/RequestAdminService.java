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

@Transactional
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

    public void deleteById(long  requestId) {
        //requestAdminRepository.deleteById(id);
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db_example", "postgres", "artur14071951");
            Statement stmt = connection.createStatement();
            stmt.executeQuery("delete from request_admins where id = " + requestId + ";");
            System.out.println("Соединение с СУБД выполнено.");
            connection.close();
            System.out.println("Отключение от СУБД выполнено.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
