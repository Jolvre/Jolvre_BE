package com.example.jolvre.exhibition.service;

import com.example.jolvre.common.error.exhibition.ExhibitNotFoundException;
import com.example.jolvre.common.service.S3Service;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitResponse;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitResponses;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitUploadRequest;
import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.exhibition.entity.ExhibitImage;
import com.example.jolvre.exhibition.repository.ExhibitImageRepository;
import com.example.jolvre.exhibition.repository.ExhibitRepository;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.service.UserService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
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
    private final UserService userService;

    @Transactional
    public void upload(ExhibitUploadRequest request, Long userId) {

        User loginUser = userService.getUserById(userId);

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

        exhibitRepository.save(exhibit);
        List<ExhibitImage> exhibitImages = new ArrayList<>();

        s3Service.uploadImageList(request.getImages()).forEach(
                url -> {
                    ExhibitImage image = ExhibitImage.builder().url(url).build();
                    exhibit.addImage(image);
                    exhibitImages.add(image);
                }
        );

        exhibitImageRepository.saveAll(exhibitImages);

        log.info("[EXHIBITION] : {}님의 {} 업로드 성공", loginUser.getNickname(), exhibit.getTitle());

    }

    @Transactional
    public void uploadAsync(ExhibitUploadRequest request, Long userId) {
        User loginUser = userService.getUserById(userId);

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

        exhibitRepository.save(exhibit);
        List<ExhibitImage> exhibitImages = new ArrayList<>();

        s3Service.uploadImageList(request.getImages()).forEach(
                url -> {
                    ExhibitImage image = ExhibitImage.builder().url(url).build();
                    exhibit.addImage(image);
                    exhibitImages.add(image);
                }
        );

        exhibitImageRepository.saveAll(exhibitImages);

        log.info("[EXHIBITION] : {}님의 {} 업로드 성공", loginUser.getNickname(), exhibit.getTitle());

    }


    public ExhibitResponse getExhibit(Long id) {
        Exhibit exhibit = exhibitRepository.findById(id)
                .orElseThrow(ExhibitNotFoundException::new);

        return ExhibitResponse.toDTO(exhibit);
    }

    @Transactional
    public ExhibitResponses getAllExhibit() {

        return ExhibitResponses.builder()
                .exhibitResponses(exhibitRepository.findAll().stream().map(
                        ExhibitResponse::toDTO
                ).collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public ExhibitResponses getAllUserExhibit(Long userId) {

        User user = userService.getUserById(userId);

        return ExhibitResponses.builder()
                .exhibitResponses(exhibitRepository.findAllByUserId(user.getId()).stream().map(
                        ExhibitResponse::toDTO
                ).collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public void delete(Long id) {
        exhibitRepository.deleteById(id);
    }

    @Transactional
    public Exhibit getExhibitById(Long id) {
        return exhibitRepository.findById(id).orElseThrow(ExhibitNotFoundException::new);
    }
}
