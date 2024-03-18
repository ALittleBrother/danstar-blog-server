package com.danstar.blog.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadFile extends BaseEntity {

    @Column(nullable = false)
    private String newFilename;

    @Column(nullable = false)
    private String originalFilename;

    @Column(nullable = false)
    private String fileExtension;

    @Column(nullable = false)
    private Long fileSize;

}
