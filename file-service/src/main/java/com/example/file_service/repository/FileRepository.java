package com.example.file_service.repository;

import com.example.file_service.dto.FileInfo;
import com.example.file_service.entity.FileManagement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Repository
public class FileRepository {

    @Value("${app.file.storage-dir}")
    String storageDir;

    @Value("${app.file.download-prefix}")
    String downloadPrefix;


    public FileInfo store(MultipartFile file) throws IOException {
        Path folder = Paths.get(storageDir);

        if (!Files.exists(folder)) {
            Files.createDirectories(folder);
        }

        String fileExtensions = StringUtils.getFilenameExtension(file.getOriginalFilename());

        String fileName = Objects.isNull(fileExtensions)
                ? UUID.randomUUID().toString()
                : UUID.randomUUID()+"."+fileExtensions;
        Path filePath = folder.resolve(fileName).normalize().toAbsolutePath();

        Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);

        return FileInfo.builder()
                .name(fileName)
                .size(file.getSize())
                .contentType(file.getContentType())
                .path(filePath.toString())
                .md5Checksum(DigestUtils.md5DigestAsHex(file.getInputStream()))
                .url(downloadPrefix+fileName)
                .build();


    }

    public Resource read(FileManagement fileManagement) throws IOException {
       var data = Files.readAllBytes(Path.of(fileManagement.getPath()));
       return new ByteArrayResource(data);
    }

}
