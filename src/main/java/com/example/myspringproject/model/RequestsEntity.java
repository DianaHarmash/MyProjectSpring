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
public class RequestsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long activityId;
    @Column(nullable = true)
    private String hours;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    private String login;
    private String activityName;


    public RequestsEntity(Long userId, Long activityId, Type type) {
        this.userId = userId;
        this.activityId = activityId;
        this.type = type;
    }

    public RequestsEntity(Long userId, Long activityId, String hours, Type type) {
        this.userId = userId;
        this.activityId = activityId;
        this.hours = hours;
        this.type = type;
    }

}
