package com.devteria.profile.repository.httpclient;

import com.devteria.profile.configuration.MultipartSupportConfig;
import com.devteria.profile.dto.ApiResponse;
import com.devteria.profile.dto.respone.FileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "file-service",url = "http://localhost:8084/file",configuration = MultipartSupportConfig.class)
public interface FileClient {
    @PostMapping(value = "/media/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<FileResponse> uploadAvatar(@RequestPart("file")MultipartFile file);
}
