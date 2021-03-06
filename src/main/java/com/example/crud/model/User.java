package com.example.crud.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Set;

@JsonIdentityInfo(
        property = "userId",
        generator = ObjectIdGenerators.PropertyGenerator.class
)
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotEmpty
    @Pattern(regexp = "[A-Za-z]{2,15}")
    @Size(min = 2, max = 15)
    @Column(unique = true)
    private String login;

    @NotEmpty
    @Size(min = 4)
    private String password;

    @Pattern(regexp = "[A-Za-zа-яёА-ЯЁ]{2,15}")
    private String firstName;

    @Pattern(regexp = "[A-Za-zа-яёА-ЯЁ]{2,15}")
    private String lastName;

    @Min(value = 0)
    @Max(value = 127)
    private byte age;

    @Pattern(regexp = "([A-z0-9_.-]+)@([A-z0-9_.-]+).([A-z]{2,8})", message = "Enter correct email")
    private String email;

    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<Role> roles;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private RequestAdmins requestAdmins;

    public RequestAdmins getRequestAdmins() {
        return requestAdmins;
    }

    public void setRequestAdmins(RequestAdmins requestAdmins) {
        this.requestAdmins = requestAdmins;
    }

    public User() {
    }

    public User(String firstName, String lastName, byte age, String email, String login, String password, Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
