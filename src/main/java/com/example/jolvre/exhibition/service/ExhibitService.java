package com.example.jolvre.exhibition.service;

import com.example.jolvre.common.error.exhibition.ExhibitNotFoundException;
import com.example.jolvre.common.service.S3Service;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitInfoResponses;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitResponse;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitUpdateRequest;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitUploadRequest;
import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.exhibition.entity.ExhibitImage;
import com.example.jolvre.exhibition.repository.DiaryRepository;
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
    private final DiaryRepository diaryRepository;

    @Transactional
    public void uploadExhibit(ExhibitUploadRequest request, Long userId) {

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

        s3Service.uploadImages(request.getImages()).forEach(
                url -> {
                    ExhibitImage image = ExhibitImage.builder().url(url).build();
                    exhibit.addImage(image);
                    exhibitImages.add(image);
                }
        );

        exhibitImageRepository.saveAll(exhibitImages);

        log.info("[EXHIBITION] : {}님의 {} 업로드 성공", loginUser.getNickname(), exhibit.getTitle());

    }
    //todo : 비동기 업로드
//    @Transactional
//    public void uploadAsync(ExhibitUploadRequest request, Long userId) {
//        User loginUser = userService.getUserById(userId);
//
//        Exhibit exhibit = Exhibit.builder()
//                .title(request.getTitle())
//                .authorWord(request.getAuthorWord())
//                .introduction(request.getIntroduction())
//                .productionMethod(request.getProductionMethod())
//                .forSale(request.isForSale())
//                .price(request.getPrice())
//                .size(request.getSize())
//                .thumbnail(s3Service.uploadImage(request.getThumbnail()))
//                .user(loginUser)
//                .build();
//
//        exhibitRepository.save(exhibit);
//        List<ExhibitImage> exhibitImages = new ArrayList<>();
//
//        s3Service.uploadImages(request.getImages()).forEach(
//                url -> {
//                    ExhibitImage image = ExhibitImage.builder().url(url).build();
//                    exhibit.addImage(image);
//                    exhibitImages.add(image);
//                }
//        );
//
//        exhibitImageRepository.saveAll(exhibitImages);
//
//        log.info("[EXHIBITION] : {}님의 {} 업로드 성공", loginUser.getNickname(), exhibit.getTitle());
//
//    }


    @Transactional
    public ExhibitResponse getExhibitInfo(Long id) {
        Exhibit exhibit = getExhibitById(id);

        return ExhibitResponse.toDTO(exhibit);
    }

    @Transactional // 배포 설정한 전시만 조회
    public ExhibitInfoResponses getAllExhibitInfo() {

        return ExhibitInfoResponses.builder()
                .exhibitResponses(exhibitRepository.findAllByDistribute(true).stream().map(
                        ExhibitResponse::toDTO
                ).collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public ExhibitInfoResponses getAllUserExhibitInfo(Long userId) {

        User user = userService.getUserById(userId);

        return ExhibitInfoResponses.builder()
                .exhibitResponses(exhibitRepository.findAllByUserId(user.getId()).stream().map(
                        ExhibitResponse::toDTO
                ).collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public void deleteExhibit(Long exhibitId, Long userId) {
        exhibitImageRepository.deleteAllByExhibitId(exhibitId);
        diaryRepository.deleteAllByExhibitId(exhibitId);

        Exhibit exhibit = getExhibitByIdAndUserId(exhibitId, userId);

        exhibitRepository.delete(exhibit);
    }

    @Transactional
    public Exhibit getExhibitById(Long id) {
        return exhibitRepository.findById(id).orElseThrow(ExhibitNotFoundException::new);
    }

    @Transactional
    public Exhibit getExhibitByIdAndUserId(Long exhibitId, Long userId) {
        return exhibitRepository.findByIdAndUserId(exhibitId, userId)
                .orElseThrow(ExhibitNotFoundException::new);
    }

    @Transactional
    public void distributeExhibit(Long exhibitId, Long userId) {
        Exhibit exhibit = getExhibitByIdAndUserId(exhibitId, userId);

        exhibit.startDistribute();

        exhibitRepository.save(exhibit);
    }

    @Transactional
    public void updateExhibit(Long exhibitId, Long userId, ExhibitUpdateRequest request) {
        Exhibit exhibit = getExhibitByIdAndUserId(exhibitId, userId);
        String thumbnail = s3Service.updateImage(request.getThumbnail(), exhibit.getThumbnail());
        exhibitImageRepository.deleteAll(exhibit.getExhibitImages());

        List<String> urls = s3Service.uploadImages(request.getImages());

        List<ExhibitImage> images = new ArrayList<>();
        urls.forEach(url -> {
            ExhibitImage image = ExhibitImage.builder().url(url).build();
            exhibit.addImage(image);
            images.add(image);
        });

        exhibitImageRepository.saveAll(images);

        exhibit.update(request, thumbnail);

        exhibitRepository.save(exhibit);
    }
}
