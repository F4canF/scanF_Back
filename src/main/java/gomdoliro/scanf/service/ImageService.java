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

    public Image saveImage(String name, String contentType, byte[] data) {
        Image image = new Image();
        image.setName(name); // 이름 저장
        image.setContentType(contentType);
        image.setData(data);
        return imageRepository.save(image);
    }

    public List<Image> getImagesByName(String name) {
        return imageRepository.findByName(name); // 이름으로 조회하는 메서드 호출
    }
}
