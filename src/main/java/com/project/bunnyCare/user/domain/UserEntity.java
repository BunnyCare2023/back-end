package com.project.bunnyCare.user.domain;

import com.project.bunnyCare.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Builder
@Entity
@Table(name = "user")
@ToString
@Getter
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

    @Column(name = "deleted_yn")
    @Setter
    private String deletedYn;

    @Setter
    private String refreshToken;


    public void registerNewUser(){
        this.deletedYn = "N";
        this.role = Role.ROLE_USER;
    }

    public void setAdminRole(){
        this.role = Role.ROLE_ADMIN;
    }

    public void delete(){
        this.deletedYn = "Y";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
