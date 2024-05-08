package service;

import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;

import com.example.jolvre.auth.jwt.service.JwtService;
import com.example.jolvre.auth.login.dto.SignUpDTO.BasicSignUpRequest;
import com.example.jolvre.auth.login.dto.SignUpDTO.TokenResponse;
import com.example.jolvre.auth.service.SignUpService;
import com.example.jolvre.user.repository.UserRepository;
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
public class SignUpServiceTest {
    @Mock
    JwtService jwtService;
    @Mock
    UserRepository userRepository;
    @Spy
    PasswordEncoder passwordEncoder;

    @InjectMocks
    SignUpService signUpService;

    @DisplayName("SignUp Basic Test")
    @Test
    void signUpBasicTest() {
        given(jwtService.createAccessToken(anyString())).willReturn("test");
        given(jwtService.createRefreshToken()).willReturn("test");

        BasicSignUpRequest request = BasicSignUpRequest.builder()
                .name("aa")
                .school("aa")
                .nickname("aa")
                .email("aa")
                .password("aa")
                .build();

        TokenResponse response = signUpService.signUpBasic(request);

        Assertions.assertEquals("test", response.getAccessToken());
    }

    @DisplayName("checkDuplicateEmail Test")
    @Test
        //True -> 중복
    void checkDuplicateEmailTest() {
        given(userRepository.existsByEmail(anyString())).willReturn(true);

        Assertions.assertTrue(signUpService.checkDuplicateEmail("test@naver.com").isDuplicate());
    }

    @DisplayName("checkDuplicateNickname Test")
    @Test
        //True -> 중복
    void checkDuplicateNicknameTest() {
        given(userRepository.existsByNickname(anyString())).willReturn(true);

        Assertions.assertTrue(signUpService.checkDuplicateNickname("test@naver.com").isDuplicate());
    }
}
