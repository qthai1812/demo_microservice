package com.example.file_service.mapper;

import com.example.file_service.dto.FileInfo;
import com.example.file_service.entity.FileManagement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileMapper {

    @Mapping(target = "id", source = "name")         // Map name -> id
    @Mapping(target = "ownerId", ignore = true)     // Bỏ qua vì FileInfo không có ownerId
    FileManagement toFileManagement(FileInfo fileInfo);
}
