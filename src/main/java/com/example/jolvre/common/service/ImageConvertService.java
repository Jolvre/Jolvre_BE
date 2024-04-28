package com.example.jolvre.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageConvertService {

    private static final String IMAGE_CONVERT_API_URI = "";

    private final WebClient webClient = WebClient.create(IMAGE_CONVERT_API_URI);

    //이미지를 받고 3d변환
    public MultipartFile convert(MultipartFile image) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", image.getResource());

        return webClient.post().uri(IMAGE_CONVERT_API_URI).
                body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(MultipartFile.class)
                .block();

    }
}
