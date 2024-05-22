package com.example.jolvre.post.dto;

import com.example.jolvre.post.entity.Category;
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
    private Category category;
    private List<MultipartFile> images;
}