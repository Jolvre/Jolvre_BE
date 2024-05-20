package service.exhibit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.example.jolvre.common.service.S3Service;
import com.example.jolvre.exhibition.dto.DiaryDTO.DiaryUpdateRequest;
import com.example.jolvre.exhibition.dto.DiaryDTO.DiaryUploadRequest;
import com.example.jolvre.exhibition.entity.Diary;
import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.exhibition.repository.DiaryRepository;
import com.example.jolvre.exhibition.repository.ExhibitRepository;
import com.example.jolvre.exhibition.service.DiaryService;
import com.example.jolvre.exhibition.service.ExhibitService;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
public class DiaryServiceTest {
    @Mock
    S3Service s3Service;
    @Mock
    ExhibitRepository exhibitRepository;
    @Mock
    ExhibitService exhibitService;
    @Mock
    DiaryRepository diaryRepository;
    @Mock
    UserService userService;

    @InjectMocks
    DiaryService diaryService;

    @Test
    @DisplayName("Upload Test")
    void uploadTest() {
        DiaryUploadRequest request = new DiaryUploadRequest("test", "test", null);
        Exhibit exhibit = Exhibit.builder().build();

        given(exhibitService.getExhibitByIdAndUserId(anyLong(), anyLong())).willReturn(exhibit);
        given(userService.getUserById(anyLong())).willReturn(new User());

        diaryService.uploadDiary(0L, 0L, request);

        verify(diaryRepository).save(any());
    }

    @Test
    @DisplayName("Get Diary Test")
    void getDiaryTest() {
        given(diaryRepository.findByIdAndExhibitIdAndUserId(anyLong(), anyLong(), anyLong())).willReturn(Optional.of(
                        Diary.builder().title("test").build()
                )
        );

        Assertions
                .assertEquals("test", diaryService.getDiaryInfo(0L, 0L).getTitle());
    }

    @Test
    @DisplayName("Get Diary Test")
    void getAllDiaryTest() {
        List<Diary> diaries = new ArrayList<>();
        diaries.add(Diary.builder().title("test").build());
        given(diaryRepository.findAllByExhibitIdAndUserId(anyLong(), anyLong())).willReturn(diaries);

        Assertions.assertEquals("test", diaryService.getAllDiaryInfo(0L)
                .getDiaryGetResponses().get(0).getTitle());
    }

    @Test
    @DisplayName("Update Diary Test")
    void updateDiaryTest() {
        MockMultipartFile file = new MockMultipartFile("test", "test.png", MediaType.IMAGE_PNG_VALUE,
                "test".getBytes());
        DiaryUpdateRequest request = DiaryUpdateRequest.builder()
                .content("asd")
                .title("asd")
                .image(file)
                .build();

        Diary diary = Diary.builder()
                .user(new User())
                .title("asdq")
                .content("qweqwe")
                .exhibit(new Exhibit())
                .build();

        String test = "test";

        given(diaryRepository.findByIdAndExhibitIdAndUserId(anyLong(), anyLong(), anyLong())).willReturn(
                Optional.of(diary));
        given(s3Service.updateImage(any(), any())).willReturn(test);

        diaryService.updateDiary(0L, 0L, 0L, request);
        verify(diaryRepository).save(any());
    }


}
