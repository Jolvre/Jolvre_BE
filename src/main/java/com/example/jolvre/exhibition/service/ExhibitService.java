package com.example.jolvre.exhibition.service;

import com.example.jolvre.common.error.exhibition.ExhibitNotFoundException;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitResponse;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitUploadRequest;
import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.exhibition.entity.ExhibitImage;
import com.example.jolvre.exhibition.repository.ExhibitImageRepository;
import com.example.jolvre.exhibition.repository.ExhibitRepository;
import com.example.jolvre.user.entity.User;
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

    public Exhibit upload(ExhibitUploadRequest request, User loginUser, List<String> paths) {

        Exhibit exhibit = Exhibit.builder()
                .title(request.getTitle())
                .authorWord(request.getAuthorWord())
                .introduction(request.getIntroduction())
                .productionMethod(request.getProductionMethod())
                .forSale(request.isForSale())
                .price(request.getPrice())
                .size(request.getSize())
                .user(loginUser)
                .build();

        List<ExhibitImage> images = paths.stream()
                .map(path -> ExhibitImage.builder()
                        .url(path)
                        .exhibit(exhibit)
                        .build()).collect(Collectors.toList());

        Exhibit save = exhibitRepository.save(exhibit);

        exhibitImageRepository.saveAll(images);

        log.info("[EXHIBITION] : {}님의 {} 업로드 성공", loginUser.getNickname(), exhibit.getTitle());

        return save;
    }

    public ExhibitResponse getExhibit(Long id) {
        Exhibit exhibit = exhibitRepository.findById(id)
                .orElseThrow(ExhibitNotFoundException::new);

        List<String> urls = exhibitImageRepository.findAllByExhibitId(id).stream()
                .map(ExhibitImage::getUrl)
                .collect(Collectors.toList());

        return ExhibitResponse.builder()
                .authorWord(exhibit.getAuthorWord())
                .forSale(exhibit.isForSale())
                .introduction(exhibit.getIntroduction())
                .price(exhibit.getPrice())
                .productionMethod(exhibit.getProductionMethod())
                .size(exhibit.getSize())
                .title(exhibit.getTitle())
                .imagesUrl(urls)
                .build();
    }

    public List<Exhibit> getAllExhibit(User user) {
        return exhibitRepository.findAllByUserId(user.getId());
    }

    public void delete(Long id) {
        exhibitRepository.deleteById(id);
    }
}
