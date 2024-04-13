package com.example.jolvre.exhibition.api;

import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.exhibition.service.DiaryPictureService;
import com.example.jolvre.exhibition.service.DiaryService;
import com.example.jolvre.exhibition.service.ExhibitPictureService;
import com.example.jolvre.exhibition.service.ExhibitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Exhibit", description = "졸업 작품 전시 API")
@RestController
@Slf4j
@RequiredArgsConstructor
public class ExhibitionController {

    private final ExhibitService exhibitService;
    private final ExhibitPictureService exhibitPictureService;
    private final DiaryPictureService diaryPictureService;
    private final DiaryService diaryService;

    @PostMapping("/api/exhibit/upload")
    public Exhibit uploadExhibit(Exhibit exhibit) {
        return exhibitService.upload(exhibit);
    }

    @PostMapping("/api/exhibit/update")
    public Exhibit updateExhibit(Exhibit exhibit) {
        return exhibitService.update(exhibit);
    }

    @PostMapping("/api/exhibit/delete")
    public void deleteExhibit(Exhibit exhibit) {
        exhibitService.delete(exhibit);
    }

}
