package com.example.jolvre.post.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class postRequest {
    private String title;
    private String content;
    private List<MultipartFile> images;
}