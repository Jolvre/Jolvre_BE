package service.user;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.example.jolvre.common.error.user.UserNotFoundException;
import com.example.jolvre.common.service.S3Service;
import com.example.jolvre.user.dto.UserDTO.UserInfoResponse;
import com.example.jolvre.user.dto.UserDTO.UserUpdateRequest;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.repository.UserRepository;
import com.example.jolvre.user.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    S3Service s3Service;

    @InjectMocks
    UserService userService;

    @DisplayName("Get User Test")
    @Test
    void getUserTest() {
        given(userRepository.findById(any(Long.class))).willReturn(
                Optional.of(
                        User.builder().name("test").build()
                ));

        UserInfoResponse user = userService.getUserInfo(1L);

        Assertions.assertEquals("test", user.getName());
    }

    @DisplayName("Get User Exception Test")
    @Test
    void getUserExceptionTest() {
        given(userRepository.findById(any(Long.class))).willThrow(new UserNotFoundException());

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserInfo(1L));
    }

    @DisplayName("Update User Test")
    @Test
    void updateUserTest() {
        User test = User.builder()
                .name("고수")
                .nickname("고수")
                .city("고수")
                .age(20)
                .imageUrl("고수")
                .build();
        UserUpdateRequest request = UserUpdateRequest.builder()
                .name("test")
                .nickname("test")
                .build();

        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(test));
        given(s3Service.updateImage(any(), any())).willReturn(any());

        userService.updateUser(0L, request);

        verify(userRepository).save(any());
    }

    @DisplayName("Update User Exception Test")
    @Test
    void updateUserExceptionTest() {
        given(userRepository.findById(any(Long.class))).willThrow(new UserNotFoundException());

        UserUpdateRequest request = UserUpdateRequest.builder()
                .build();

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, request));
    }

}
