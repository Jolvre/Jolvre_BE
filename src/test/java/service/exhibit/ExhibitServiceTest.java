package service.exhibit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.example.jolvre.common.service.S3Service;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitInfoResponses;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitInvitationResponse;
import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.exhibition.repository.DiaryRepository;
import com.example.jolvre.exhibition.repository.ExhibitCommentRepository;
import com.example.jolvre.exhibition.repository.ExhibitImageRepository;
import com.example.jolvre.exhibition.repository.ExhibitQueryDslRepository;
import com.example.jolvre.exhibition.repository.ExhibitRepository;
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

@ExtendWith(MockitoExtension.class)
public class ExhibitServiceTest {

    @InjectMocks
    ExhibitService exhibitService;
    @Mock
    ExhibitRepository exhibitRepository;
    @Mock
    ExhibitImageRepository exhibitImageRepository;
    @Mock
    S3Service s3Service;
    @Mock
    UserService userService;
    @Mock
    DiaryRepository diaryRepository;
    @Mock
    ExhibitCommentRepository exhibitCommentRepository;
    @Mock
    ExhibitQueryDslRepository exhibitQueryDslRepository;


    @DisplayName("Get All Exhibit Info By Distribute")
    @Test
    void getAllExhibitInfoTest() {
        List<Exhibit> exhibits = new ArrayList<>();
        User test = User.builder().build();
        Exhibit exhibit1 = Exhibit.builder()
                .user(test)
                .build();

        Exhibit exhibit2 = Exhibit.builder()
                .user(test)
                .build();

        exhibit1.startDistribute();
        exhibit2.startDistribute();

        exhibits.add(exhibit1);

        given(exhibitRepository.findAllByDistribute(true)).willReturn(exhibits);

        ExhibitInfoResponses exhibitInfos = exhibitService.getAllExhibitInfo();

        exhibitInfos.getExhibitResponses().forEach(
                ex -> Assertions.assertTrue(ex.isDistribute())
        );
    }

    @DisplayName("Get All Exhibit Info By User")
    @Test
    void getAllExhibitInfoByUseTest() {
        List<Exhibit> exhibits = new ArrayList<>();
        User test = User.builder().build();
        test.setId(0L);

        Exhibit exhibit1 = Exhibit.builder()
                .user(test)
                .build();

        Exhibit exhibit2 = Exhibit.builder()
                .user(test)
                .build();

        exhibits.add(exhibit1);
        exhibits.add(exhibit2);

        given(exhibitRepository.findAllByUserId(0L)).willReturn(exhibits);
        given(userService.getUserById(0L)).willReturn(test);

        ExhibitInfoResponses exhibitInfos = exhibitService.getAllUserExhibitInfo(0L);

        exhibitInfos.getExhibitResponses().forEach(
                ex -> Assertions.assertEquals(0L, ex.getUserInfoResponse().getId()));
    }

    @DisplayName("Distribute Exhibit Test")
    @Test
    void distributeExhibitTest() {
        User user = User.builder().build();
        user.setId(0L);
        Exhibit exhibit = Exhibit.builder().build();
        exhibit.setId(0L);

        given(exhibitRepository.findByIdAndUserId(0L, 0L)).willReturn(Optional.of(exhibit));

        exhibitService.distributeExhibit(0L, 0L);

        verify(exhibitRepository).save(exhibit);
    }

    @DisplayName("create Invitation Test")
    @Test
    void createInvitationTest() {
        User user = User.builder().build();
        user.setId(0L);
        Exhibit exhibit = Exhibit.builder()
                .title("test")
                .build();
        exhibit.setId(0L);

        given(exhibitRepository.findById(0L)).willReturn(Optional.of(exhibit));

        ExhibitInvitationResponse invitation = exhibitService.createInvitation(0L);

        Assertions.assertEquals("test", invitation.getTitle());
    }

    @DisplayName("Get Exhibit Info By Keyword Test")
    void getExhibitInfoByKeywordTest() {
        User user = User.builder().build();
        user.setId(0L);
        Exhibit exhibit = Exhibit.builder()
                .title("test")
                .build();
        exhibit.setId(0L);
    }

    @DisplayName("Get Exhibit Info By Keyword Null Test")
    void getExhibitInfoByKeywordNullTest() {
        User user = User.builder().build();
        user.setId(0L);
        Exhibit exhibit = Exhibit.builder()
                .title("test")
                .build();
        exhibit.setId(0L);
    }
}

