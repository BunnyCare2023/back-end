package com.project.bunnyCare.image.infrastructure;

import com.project.bunnyCare.image.domain.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

}
