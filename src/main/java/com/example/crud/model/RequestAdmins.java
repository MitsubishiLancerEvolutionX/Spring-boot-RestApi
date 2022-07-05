package com.example.crud.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;

@JsonIdentityInfo(
        property = "id",
        generator = ObjectIdGenerators.PropertyGenerator.class
)
@Entity
@Table(name = "request_admins")
public class RequestAdmins {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;



    public RequestAdmins() {
    }

    public RequestAdmins(User user) {
        this.user = user;
    }

    @JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
