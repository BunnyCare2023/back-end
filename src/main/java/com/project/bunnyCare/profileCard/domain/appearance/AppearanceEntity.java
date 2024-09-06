package com.project.bunnyCare.profileCard.domain.appearance;

import com.project.bunnyCare.common.BaseEntity;
import com.project.bunnyCare.profileCard.domain.ProfileCardEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "appearance")
@Entity
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppearanceEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 생김새 타입
    @Enumerated(EnumType.STRING)
    private AppearanceType type;

    @ManyToOne
    private ProfileCardEntity profile;

    @Column(name = "delete_yn", length = 1)
    private Character deleteYn;

    public void delete(){
        this.deleteYn = 'Y';
    }

    public void updateProfileCard(ProfileCardEntity profile){
        this.profile = profile;
    }

    public static AppearanceEntity create(AppearanceType type) {
        return new AppearanceEntity(type);
    }

    private AppearanceEntity(AppearanceType type) {
        this.type = type;
        this.deleteYn = 'N';
    }
}
