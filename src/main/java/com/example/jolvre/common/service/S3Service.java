package com.example.jolvre.common.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3Service {

    public String uploadImage(MultipartFile multipart) {
        return "aaa.aaaa.com";
    }

    public List<String> uploadImageList(List<MultipartFile> multiparts) {
        if (multiparts == null) {
            return new ArrayList<>();
        }

        List<String> strings = new ArrayList<>();
        strings.add("aaa.aaa.com");
        strings.add("bbb.bbb.com");

        return strings;
    }
}
