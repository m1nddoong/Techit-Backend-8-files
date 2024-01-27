package com.example.contents.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // 사용자 이름이 겹치지 않게 해줌
    private String username;
    private String email;
    @Setter
    private String phone;
    @Setter
    private String bio;
    @Setter
    private String avatar; // 이미지

    public User(String username, String email, String phone, String bio) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.bio = bio;
    }
}
