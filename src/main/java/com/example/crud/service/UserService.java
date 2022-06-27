package com.example.crud.service;

import com.example.crud.model.RequestAdmins;
import com.example.crud.model.Role;
import com.example.crud.model.User;
import com.example.crud.repository.RoleRepository;
import com.example.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User passwordCoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    public User getById(long id) {
        User user = null;
        Optional<User> optional = userRepository.findById(id);
        if(optional.isPresent()) {
            user = optional.get();
        }
        return user;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void save(User user) {
        userRepository.save(passwordCoder(user));
    }

    public void save(User user, boolean hashPassword) {
        if(hashPassword) {
            user = passwordCoder(user);
        }
        userRepository.save(user);
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    //@PostConstruct
    public void addDefaultUser() {
        roleRepository.save(new Role("ROLE_USER"));
        roleRepository.save(new Role("ROLE_ADMIN"));
        Set<Role> roles1 = new HashSet<>();
        Set<Role> roles2 = new HashSet<>();
        roles1.add(roleRepository.findById(1L).orElse(null));
        roles2.add(roleRepository.findById(1L).orElse(null));
        roles2.add(roleRepository.findById(2L).orElse(null));
        User user1 = new User("Артур","Евлантьев", (byte) 24, "artur@mail.ru", "user", "user", roles1);
        User user2 = new User("Максим","Константинов",(byte) 24, "max@mail.ru", "admin", "admin", roles2);
        save(user1);
        save(user2);
    }
}
