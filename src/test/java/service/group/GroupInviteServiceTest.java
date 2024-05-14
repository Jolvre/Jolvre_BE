package service.group;

import com.example.jolvre.group.GroupRoleChecker;
import com.example.jolvre.group.repository.GroupExhibitRepository;
import com.example.jolvre.group.repository.GroupInviteStateRepository;
import com.example.jolvre.group.repository.MemberRepository;
import com.example.jolvre.group.service.GroupInviteService;
import com.example.jolvre.user.service.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GroupInviteServiceTest {

    @Mock
    UserService userService;
    @Mock
    GroupExhibitRepository groupExhibitRepository;
    @Mock
    GroupInviteStateRepository groupInviteStateRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    GroupRoleChecker checker;

    @InjectMocks
    GroupInviteService groupInviteService;

}
