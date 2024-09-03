package com.project.bunnyCare.user.repository.entity;

import com.project.bunnyCare.common.BaseEntity;
import com.project.bunnyCare.user.domain.Role;
import com.project.bunnyCare.user.domain.SocialType;
import com.project.bunnyCare.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Entity
@Table(name = "user")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "social_type")
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(name ="role")
    @Enumerated(EnumType.STRING)
    private Role role;


    public User toUser(){
        return User.builder()
                .id(id)
                .email(email)
                .name(name)
                .socialType(socialType)
                .role(role)
                .build();
    }
}
