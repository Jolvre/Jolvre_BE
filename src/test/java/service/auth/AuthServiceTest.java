package service.auth;

import com.example.jolvre.auth.service.AuthService;
import com.example.jolvre.user.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    WebClient webClient;

    @Spy
    @InjectMocks
    AuthService authService;

}
