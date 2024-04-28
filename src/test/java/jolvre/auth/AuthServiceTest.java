package jolvre.auth;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.jolvre.auth.login.dto.SignUpDTO.BasicSignUpRequest;
import com.example.jolvre.auth.service.AuthService;
import com.example.jolvre.user.entity.Role;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Spy
    @InjectMocks
    AuthService authService;

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 테스트")
    void signUpTest() {

        BasicSignUpRequest signUp =
                BasicSignUpRequest.builder()
                        .password("11")
                        .age(20)
                        .school("uos")
                        .name("고수")
                        .city("서울")
                        .email("a@nnn")
                        .nickname("이얍")
                        .build();

        when(userRepository.save(any())).thenReturn(any());

        authService.signUpBasic(signUp);

        verify(authService).signUpBasic(signUp);
    }

    @DisplayName("유저 -> 학생 권한 변경")
    @Test
    public void updateAuthorizeTest() {
        User user = new User();

        user.setRole(Role.USER);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User updateUser = authService.updateAuthorize(user);

        Assertions.assertEquals(Role.STUDENT, updateUser.getRole());
    }
}
