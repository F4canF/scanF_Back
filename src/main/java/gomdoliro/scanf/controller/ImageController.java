package gomdoliro.scanf.controller;

import gomdoliro.scanf.domain.Image;
import gomdoliro.scanf.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    // 로그인된 사용자의 이메일로 이미지 저장
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            String email = getCurrentUserEmail(); // 로그인된 사용자의 이메일 가져오기
            byte[] data = file.getBytes();
            String contentType = file.getContentType();
            imageService.saveImage(email, contentType, data);
            return ResponseEntity.status(HttpStatus.CREATED).body("Image uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }
    }

    // 특정 이메일로 저장된 모든 이미지 바이너리 반환
    @GetMapping("/download/{email}")
    public ResponseEntity<List<byte[]>> downloadImagesByEmail(@PathVariable String email) {
        List<Image> images = imageService.getImagesByEmail(email); // 이메일로 모든 이미지 조회
        if (images.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 이미지가 없을 경우 404
        }

        List<byte[]> imageBytes = images.stream()
                .map(Image::getData)
                .toList(); // 이미지 데이터를 리스트로 변환

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON) // JSON 형식으로 반환
                .body(imageBytes); // 이미지 바이너리 데이터 리스트 반환
    }

    // 로그인된 사용자의 이메일 가져오는 메서드
    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); // 이메일을 사용자 이름으로 사용하는 경우
    }
}
