package com.example.jolvre.exhibition.service;

import com.example.jolvre.common.error.exhibition.ExhibitNotFoundException;
import com.example.jolvre.common.error.user.UserNotFoundException;
import com.example.jolvre.common.service.S3Service;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitResponse;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitResponses;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitUploadRequest;
import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.exhibition.entity.ExhibitImage;
import com.example.jolvre.exhibition.repository.ExhibitImageRepository;
import com.example.jolvre.exhibition.repository.ExhibitRepository;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExhibitService {
    private final ExhibitRepository exhibitRepository;
    private final ExhibitImageRepository exhibitImageRepository;
    private final S3Service s3Service;
    private final UserRepository userRepository;

    public void upload(ExhibitUploadRequest request, Long userId) {

        User loginUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Exhibit exhibit = Exhibit.builder()
                .title(request.getTitle())
                .authorWord(request.getAuthorWord())
                .introduction(request.getIntroduction())
                .productionMethod(request.getProductionMethod())
                .forSale(request.isForSale())
                .price(request.getPrice())
                .size(request.getSize())
                .thumbnail(s3Service.uploadImage(request.getThumbnail()))
                .user(loginUser)
                .build();

        List<ExhibitImage> images = s3Service.uploadImageList(request.getImages()).stream()
                .map(path -> ExhibitImage.builder()
                        .url(path)
                        .exhibit(exhibit)
                        .build()).collect(Collectors.toList());

        exhibitRepository.save(exhibit);

        exhibitImageRepository.saveAll(images);

        log.info("[EXHIBITION] : {}님의 {} 업로드 성공", loginUser.getNickname(), exhibit.getTitle());

    }

    public ExhibitResponse getExhibit(Long id) {
        Exhibit exhibit = exhibitRepository.findById(id)
                .orElseThrow(ExhibitNotFoundException::new);

        List<String> urls = exhibitImageRepository.findAllByExhibitId(id).stream()
                .map(ExhibitImage::getUrl)
                .collect(Collectors.toList());

        return ExhibitResponse.builder()
                .id(exhibit.getId())
                .authorWord(exhibit.getAuthorWord())
                .forSale(exhibit.isForSale())
                .introduction(exhibit.getIntroduction())
                .price(exhibit.getPrice())
                .productionMethod(exhibit.getProductionMethod())
                .size(exhibit.getSize())
                .title(exhibit.getTitle())
                .thumbnail(exhibit.getThumbnail())
                .imagesUrl(urls)
                .build();
    }

    public ExhibitResponses getAllExhibit(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return ExhibitResponses.builder()
                .exhibitResponses(exhibitRepository.findAllByUserId(user.getId()).stream().map(
                        exhibit -> ExhibitResponse.toDTO(exhibit, null)
                ).collect(Collectors.toList()))
                .build();
    }

    public void delete(Long id) {
        exhibitRepository.deleteById(id);
    }
}
