package com.example.file_service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class FileInfo {
    String name;
    String contentType;
    long size;
    String md5Checksum;
    String path;
    String url;
}
