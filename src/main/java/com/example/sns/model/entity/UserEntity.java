package com.example.sns.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table
public class UserEntity {

    @Id
    private Integer id;

    @Column(name = "user_name")
    private String username;

    private String password;

}
