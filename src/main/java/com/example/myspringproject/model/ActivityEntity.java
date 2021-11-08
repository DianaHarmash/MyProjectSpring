package com.example.myspringproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private String category;

    @ManyToMany(targetEntity = UserEntity.class, mappedBy = "activities", cascade = CascadeType.ALL)
    private Set<UserEntity> users;

    public ActivityEntity(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public void setUser(UserEntity user) {
        users.add(user);
    }
}
