package com.danstar.blog.server.repository.uploadfile;

import com.danstar.blog.server.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
}
