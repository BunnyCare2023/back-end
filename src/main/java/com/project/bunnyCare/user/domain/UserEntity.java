package com.project.bunnyCare.user.domain;

import com.project.bunnyCare.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    private String deletedYn;

    @Column(name = "deleted_date")
    private LocalDate deletedDate;

    @Column(name = "deleted_reason", length = 4000)
    private String deletedReason;

    @Setter
    private String refreshToken;


    public void createUser(){
        this.deletedYn = "N";
        this.role = Role.ROLE_USER;
    }

    public void setAdminRole(){
        this.role = Role.ROLE_ADMIN;
    }

    public boolean isDeleted() {
        return this.deletedYn.equals("Y");
    }

    public void delete(String deletedReason){
        this.deletedYn = "Y";
        this.deletedDate = LocalDate.now();
        this.deletedReason = deletedReason;
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
