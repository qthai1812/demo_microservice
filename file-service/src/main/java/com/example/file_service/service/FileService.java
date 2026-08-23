package com.example.file_service.service;

import com.example.file_service.dto.FileInfo;
import com.example.file_service.dto.response.FileData;
import com.example.file_service.dto.response.FileResponse;
import com.example.file_service.entity.FileManagement;
import com.example.file_service.exception.AppException;
import com.example.file_service.exception.ErrorCode;
import com.example.file_service.mapper.FileMapper;
import com.example.file_service.repository.FileManagementRepository;
import com.example.file_service.repository.FileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FileService {

    FileManagementRepository fileManagementRepository;
    FileRepository fileRepository;
    FileMapper fileMapper;

    public FileResponse uploadFile(MultipartFile file) throws IOException {

      FileInfo fileInfo = fileRepository.store(file);

      FileManagement fileManagement = fileMapper.toFileManagement(fileInfo);

      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      Jwt jwt =(Jwt) authentication.getPrincipal();

      String userId = jwt.getClaim("userId");

      fileManagement.setOwnerId(userId);

      fileManagementRepository.save(fileManagement);

      return FileResponse.builder()
              .originalFileName(file.getOriginalFilename())
              .url(fileInfo.getUrl())
              .build();
    }

    public FileData download(String fileName) throws IOException {
       FileManagement fileManagement = fileManagementRepository.findById(fileName)
                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));
       var resource = fileRepository.read(fileManagement);
        return new FileData(fileManagement.getContentType(),resource);
    }


}
