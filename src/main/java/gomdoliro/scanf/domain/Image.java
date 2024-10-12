package gomdoliro.scanf.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 저장하는 사용자의 이메일
    private String email;

    // 이미지 타입 (예: jpg, png 등)
    private String contentType;

    @Lob
    @Column(name = "data", columnDefinition = "LONGBLOB")
    private byte[] data;
}