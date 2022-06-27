package com.example.crud.controller;

import com.example.crud.model.RequestAdmins;
import com.example.crud.model.Role;
import com.example.crud.model.User;
import com.example.crud.repository.RoleRepository;
import com.example.crud.service.RequestAdminService;
import com.example.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class RequestAdminController {

    @Autowired
    private RequestAdminService requestAdminService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/admin-requests")
    public ResponseEntity<List<RequestAdmins>> getAdminRequests() {
        return new ResponseEntity<>(requestAdminService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/admin-requests")
    public ResponseEntity<?> createAdminRequest(@Valid @RequestBody RequestAdmins requestAdmins) {
        requestAdminService.save(requestAdmins);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/admin-requests/{id}/appoint")
    public ResponseEntity<?> appointAdminRequest(@PathVariable("id") long id) {
        RequestAdmins requestAdmins = requestAdminService.getById(id);
        User user = requestAdmins.getUser();
        Set<Role> roles = user.getRoles();
        roles.add(roleRepository.findById(2L).orElse(null));
        user.setRoles(roles);
        userService.save(user, false);
        requestAdminService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/admin-requests/{id}")
    public ResponseEntity<?> deleteAdminRequest(@PathVariable("id") long id) {
        requestAdminService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("admin-requests/{id}")
    public ResponseEntity<RequestAdmins> getAdminRequest(@PathVariable("id") long id) {
        RequestAdmins requestAdmins = requestAdminService.getById(id);
        return new ResponseEntity<>(requestAdmins, HttpStatus.OK);
    }
}
