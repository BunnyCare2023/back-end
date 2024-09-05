package com.project.bunnyCare.image.domain;

import com.project.bunnyCare.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "image")
@Entity
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImageEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ImageType type;

    @Column(name = "size")
    private Long size;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "stored_name")
    private String storedName;

    @Column(name = "stored_path")
    private String storedPath;

    @Column(name = "stored_extension")
    private String storedExtension;

    @Column(name = "delete_yn", length = 1)
    private Character deleteYn;

    public void deleteImage(){
        this.deleteYn = 'Y';
    }

    public ImageEntity(ImageType type, Long size, String originalName, String storedName, String storedPath, String storedExtension, Character deleteYn) {
        this.type = type;
        this.size = size;
        this.originalName = originalName;
        this.storedName = storedName;
        this.storedPath = storedPath;
        this.storedExtension = storedExtension;
        this.deleteYn = deleteYn;
    }
}
