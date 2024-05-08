package service;

import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.example.jolvre.common.service.S3Service;
import com.example.jolvre.exhibition.dto.DiaryDTO.DiaryUploadRequest;
import com.example.jolvre.exhibition.entity.Diary;
import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.exhibition.repository.DiaryRepository;
import com.example.jolvre.exhibition.repository.ExhibitRepository;
import com.example.jolvre.exhibition.service.DiaryService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DiaryServiceTest {
    @Spy
    S3Service s3Service;
    @Mock
    ExhibitRepository exhibitRepository;
    @Mock
    DiaryRepository diaryRepository;

    @Spy
    @InjectMocks
    DiaryService diaryService;

    @Test
    @DisplayName("Upload Test")
    void uploadTest() {
        DiaryUploadRequest request = new DiaryUploadRequest();

        given(exhibitRepository.findById(anyLong())).willReturn(Optional.of(new Exhibit()));

        diaryService.upload(0L, request);

        verify(diaryService).upload(0L, request);
    }

    @Test
    @DisplayName("Get Diary Test")
    void getDiaryTest() {
        given(diaryRepository.findByIdAndExhibitId(anyLong(), anyLong())).willReturn(Optional.of(
                        Diary.builder().title("test").build()
                )
        );

        Assertions.assertEquals("test", diaryService.getDiary(0L, 0L).getTitle());
    }

    @Test
    @DisplayName("Get Diary Test")
    void getAllDiaryTest() {
        List<Diary> diaries = new ArrayList<>();
        diaries.add(Diary.builder().title("test").build());
        given(diaryRepository.findAllByExhibitId(anyLong())).willReturn(diaries);

        Assertions.assertEquals("test", diaryService.getAllDiary(0L).getDiaryGetResponses().get(0).getTitle());
    }

}
