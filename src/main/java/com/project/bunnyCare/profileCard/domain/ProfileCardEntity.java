package com.project.bunnyCare.profileCard.domain;

import com.project.bunnyCare.common.BaseEntity;
import com.project.bunnyCare.image.domain.ImageEntity;
import com.project.bunnyCare.profileCard.domain.appearance.AppearanceEntity;
import com.project.bunnyCare.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Table(name = "profile_card")
@Entity
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rabbit_name", length = 15, nullable = false)
    private String rabbitName;

    @Column(name="birth_date")
    private LocalDate birthDate;

    @Column(name = "adoption_date")
    private LocalDate adoptionDate;

    @Column(name = "sex", length = 1)
    private Character sex;

    @OneToOne
    private ImageEntity profileImage;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private List<AppearanceEntity> appearances;

    @ManyToOne
    private UserEntity user;

    @Column(name = "delete_yn", length = 1)
    private Character deleteYn;

    public void delete(){
        this.deleteYn = 'Y';
    }

    public void update(String rabbitName, LocalDate birthDate, LocalDate adoption, Character sex){
        this.rabbitName = rabbitName;
        this.birthDate = birthDate;
        this.adoptionDate = adoption;
        this.sex = sex;
    }


    public ProfileCardEntity(String rabbitName, LocalDate birthDate, LocalDate adoptionDate, Character sex, ImageEntity profileImage, List<AppearanceEntity> appearances, UserEntity user, Character deleteYn) {
        this.rabbitName = rabbitName;
        this.birthDate = birthDate;
        this.adoptionDate = adoptionDate;
        this.sex = sex;
        this.profileImage = profileImage;
        this.appearances = appearances;
        this.user = user;
        this.deleteYn = deleteYn;
    }
}
