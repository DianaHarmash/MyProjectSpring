package com.example.myspringproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String login;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(targetEntity = ActivityEntity.class, cascade = CascadeType.ALL)
    private List<ActivityEntity> activities;

    public UserEntity(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public void setActivity(ActivityEntity activity) {
        this.activities.add(activity);
    }
}
