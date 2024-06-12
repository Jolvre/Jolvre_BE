package service.group;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.example.jolvre.common.error.group.GroupInviteDuplicateException;
import com.example.jolvre.group.GroupRoleChecker;
import com.example.jolvre.group.entity.GroupExhibit;
import com.example.jolvre.group.entity.GroupInviteState;
import com.example.jolvre.group.entity.GroupRole;
import com.example.jolvre.group.entity.InviteState;
import com.example.jolvre.group.entity.Member;
import com.example.jolvre.group.repository.GroupExhibitRepository;
import com.example.jolvre.group.repository.GroupInviteStateRepository;
import com.example.jolvre.group.repository.MemberRepository;
import com.example.jolvre.group.service.GroupInviteService;
import com.example.jolvre.user.entity.User;
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
public class GroupInviteServiceTest {

    @InjectMocks
    GroupInviteService groupInviteService;
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

    @DisplayName("Invite User Test")
    @Test
    void inviteUserTest() {
        User fromUser = User.builder()
                .nickname("from")
                .build();
        Member manager = Member.builder()
                .user(fromUser)
                .groupRole(GroupRole.MANAGER)
                .build();

        User toUser = User.builder()
                .nickname("to")
                .build();

        GroupExhibit group = GroupExhibit.builder()
                .build();

        group.addMember(manager);

        given(userService.getUserById(0L)).willReturn(fromUser);
        given(userService.getUserByNickname("to")).willReturn(toUser);
        given(groupExhibitRepository.findById(0L)).willReturn(Optional.of(group));

        groupInviteService.inviteUser(0L, "to", 0L);

        verify(groupInviteStateRepository).save(any());
    }

    @DisplayName("Invite User Test (Duplicate Invite)")
    @Test
    void inviteUserExceptionTest() {
        User fromUser = User.builder()
                .nickname("from")
                .build();
        Member manager = Member.builder()
                .user(fromUser)
                .groupRole(GroupRole.MANAGER)
                .build();

        User toUser = User.builder()
                .nickname("to")
                .build();
        toUser.setId(0L);

        GroupExhibit group = GroupExhibit.builder()
                .build();

        group.addMember(manager);

        given(userService.getUserById(0L)).willReturn(fromUser);
        given(userService.getUserByNickname("to")).willReturn(toUser);
        given(groupExhibitRepository.findById(0L)).willReturn(Optional.of(group));
        given(groupInviteStateRepository.existsByGroupExhibitIdAndUserId(0L, 0L))
                .willThrow(new GroupInviteDuplicateException()); //이미 초대된 멤버

        Assertions.assertThrows(GroupInviteDuplicateException.class,
                () -> groupInviteService.inviteUser(0L, "to", 0L));
    }

    @DisplayName("Check Invite Status Accept Test")
    @Test
    void checkInviteStatusAcceptTest() {
        User user = User.builder().build();
        GroupExhibit group = GroupExhibit.builder().build();

        GroupInviteState state = GroupInviteState.builder()
                .inviteState(InviteState.PEND)
                .user(user)
                .groupExhibit(group)
                .build();

        given(groupInviteStateRepository.findByIdAndUserId(0L, 0L)).willReturn(Optional.of(state));

        groupInviteService.checkInviteStatus(0L, InviteState.ACCEPT, 0L);

        verify(groupExhibitRepository).save(group);
    }

    @DisplayName("Check Invite Status Refuse Test")
    @Test
    void checkInviteStatusRefuseTest() {
        User user = User.builder().build();
        GroupExhibit group = GroupExhibit.builder().build();

        GroupInviteState state = GroupInviteState.builder()
                .inviteState(InviteState.PEND)
                .user(user)
                .groupExhibit(group)
                .build();

        given(groupInviteStateRepository.findByIdAndUserId(0L, 0L)).willReturn(Optional.of(state));

        groupInviteService.checkInviteStatus(0L, InviteState.REFUSE, 0L);

        verify(groupInviteStateRepository).delete(state);
    }
}
