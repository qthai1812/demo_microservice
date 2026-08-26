package com.devteria.profile.repository.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.devteria.profile.configuration.MultipartSupportConfig;
import com.devteria.profile.dto.ApiResponse;
import com.devteria.profile.dto.respone.FileResponse;

// file-service: port 8081, context-path: /file
// → endpoint thực tế: http://file-service:8081/file/media/upload
@FeignClient(
        value = "file-service",
        url = "${app.services.file.url:http://localhost:8081}",
        configuration = MultipartSupportConfig.class)
public interface FileClient {
    @PostMapping(value = "/file/media/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<FileResponse> uploadAvatar(@RequestPart("file") MultipartFile file);
}
