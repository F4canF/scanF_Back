package gomdoliro.scanf.service;

import gomdoliro.scanf.domain.Image;
import gomdoliro.scanf.domain.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image saveImage(String email, String contentType, byte[] data) {
        Image image = new Image();
        image.setEmail(email);
        image.setContentType(contentType);
        image.setData(data);
        return imageRepository.save(image);
    }

    public List<Image> getImagesByEmail(String email) {
        return imageRepository.findByEmail(email);
    }
}