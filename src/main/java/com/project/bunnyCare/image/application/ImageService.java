package com.project.bunnyCare.image.application;

import com.project.bunnyCare.common.exception.ApiException;
import com.project.bunnyCare.image.domain.ImageEntity;
import com.project.bunnyCare.image.domain.ImageResponseCode;
import com.project.bunnyCare.image.domain.ImageStore;
import com.project.bunnyCare.image.domain.ImageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {

    @Value("${file.upload.path}")
    private String uploadPath;

    private final ImageStore imageStore;

    @Transactional
    public ImageEntity uploadImage(MultipartFile file, ImageType type) {
        String originalFileName = file.getOriginalFilename();
        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String saveFileName = uuid+ext;
        Long size = file.getSize();

        String savePath = Paths.get(uploadPath, saveFileName).toString();
        try {
            file.transferTo(new File(savePath));
        } catch (IOException e) {
            throw new ApiException(ImageResponseCode.IMAGE_UPLOAD_FAILED);
        }

        ImageEntity newImage = new ImageEntity(type, size, originalFileName, saveFileName, savePath, ext, 'N');

        return imageStore.save(newImage);
    }

    @Transactional
    public void deleteImage(ImageEntity profileImage) {
        profileImage.deleteImage();
        boolean isDeleted = false;
        try{
            File file = new File(profileImage.getStoredPath());
            if(file.exists()){
                isDeleted = file.delete();
            }
        }catch (Exception e){
            throw new ApiException(ImageResponseCode.IMAGE_DELETE_FAILED);
        }
        if(isDeleted){
            log.info("Image delete success");
        }else {
            log.info("Image delete failed");
        }
    }
}
