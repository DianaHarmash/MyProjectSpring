package com.example.myspringproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserActivityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long activityId;
    @Column(nullable = false)
    private String hours;

    public UserActivityEntity(Long userId, Long activityId, String hours) {
        this.userId = userId;
        this.activityId = activityId;
        this.hours = hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }
}
