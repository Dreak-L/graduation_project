package com.example.graduation_project.Domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class FileUpload {
    @Value("${web.patient-upload-path}")
    private String Patient_upload_path;
}
