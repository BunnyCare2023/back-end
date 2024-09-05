package com.project.bunnyCare.image.infrastructure;

import com.project.bunnyCare.image.domain.ImageEntity;
import com.project.bunnyCare.image.domain.ImageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ImageStoreImpl implements ImageStore {

    private final ImageRepository imageRepository;

    @Override
    public ImageEntity save(ImageEntity imageEntity) {
        return imageRepository.save(imageEntity);
    }
}
