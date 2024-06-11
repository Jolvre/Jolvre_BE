package service.group;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;

import com.example.jolvre.common.error.user.UserAccessDeniedException;
import com.example.jolvre.common.service.S3Service;
import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.exhibition.service.ExhibitService;
import com.example.jolvre.group.GroupRoleChecker;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupExhibitUserResponses;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupInvitationResponse;
import com.example.jolvre.group.entity.GroupExhibit;
import com.example.jolvre.group.entity.GroupRole;
import com.example.jolvre.group.entity.Member;
import com.example.jolvre.group.repository.GroupExhibitRepository;
import com.example.jolvre.group.repository.MemberRepository;
import com.example.jolvre.group.repository.RegisteredExhibitRepository;
import com.example.jolvre.group.service.GroupExhibitService;
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
public class GroupExhibitServiceTest {
    @Mock
    GroupExhibitRepository groupExhibitRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    RegisteredExhibitRepository registeredExhibitRepository;
    @Mock
    UserService userService;
    @Mock
    ExhibitService exhibitService;
    @Mock
    GroupRoleChecker checker;
    @Mock
    S3Service s3Service;

    @InjectMocks
    GroupExhibitService groupExhibitService;

    @DisplayName("Add Exhibit Test")
    @Test
    void addExhibitTest() {
        User test = User.builder().build();

        Member manager = Member.builder().user(test).groupRole(GroupRole.MEMBER).build();

        Exhibit exhibit = Exhibit.builder()
                .title("test")
                .user(test)
                .build();

        GroupExhibit group = GroupExhibit.builder()
                .build();
        group.addMember(manager);

        given(userService.getUserById(0L)).willReturn(test);
        given(exhibitService.getExhibitById(0L)).willReturn(exhibit);
        given(groupExhibitRepository.findById(0L)).willReturn(Optional.of(group));

        groupExhibitService.addExhibit(0L, 0L, 0L);

        verify(groupExhibitRepository).save(group);
    }

    @DisplayName("Add Manager Test")
    @Test
    void addManagerTest() {
        User from = User.builder().build();
        User to = User.builder().build();

        GroupExhibit group = GroupExhibit.builder().build();

        Member manager = Member.builder()
                .user(from)
                .groupRole(GroupRole.MANAGER)
                .build();

        Member member = Member.builder()
                .user(to)
                .groupRole(GroupRole.MEMBER)
                .build();

        group.addMember(manager);
        group.addMember(member);

        given(userService.getUserById(0L)).willReturn(from);
        given(userService.getUserById(1L)).willReturn(to);
        given(groupExhibitRepository.findById(0L)).willReturn(Optional.of(group));
        given(memberRepository.findByUserIdAndGroupExhibitId(1L, 0L)).willReturn(Optional.of(member));

        groupExhibitService.addManager(0L, 1L, 0L);

        verify(groupExhibitRepository).save(group);
    }

    @DisplayName("Add Manager Exception Test (Access Denied) (From User is not Manager)")
    @Test
    void addManagerExceptionFromIsNotManagerTest() {
        User from = User.builder().build();
        User to = User.builder().build();

        GroupExhibit group = GroupExhibit.builder().build();

        Member manager = Member.builder()
                .user(from)
                .groupRole(GroupRole.MEMBER) // 초대하는 사람은 매니저여야 함
                .build();

        Member member = Member.builder()
                .user(to)
                .groupRole(GroupRole.MEMBER)
                .build();

        group.addMember(manager);
        group.addMember(member);

        given(userService.getUserById(0L)).willReturn(from);
        given(userService.getUserById(1L)).willReturn(to);
        given(groupExhibitRepository.findById(0L)).willReturn(Optional.of(group));

        willThrow(new UserAccessDeniedException()).given(checker).isManager(group, from);

        Assertions.assertThrows(UserAccessDeniedException.class,
                () -> groupExhibitService.addManager(0L, 1L, 0L));
    }

    @DisplayName("Add Manager Exception Test (Access Denied) (To User is not Member)")
    @Test
    void addManagerExceptionToIsNotMemberTest() {
        User from = User.builder().build();
        User to = User.builder().build(); // to 가 멤버여야함

        GroupExhibit group = GroupExhibit.builder().build();

        Member manager = Member.builder()
                .user(from)
                .groupRole(GroupRole.MANAGER) // 초대하는 사람은 매니저여야 함
                .build();

        group.addMember(manager);

        given(userService.getUserById(0L)).willReturn(from);
        given(userService.getUserById(1L)).willReturn(to);
        given(groupExhibitRepository.findById(0L)).willReturn(Optional.of(group));

        willThrow(new UserAccessDeniedException()).given(checker).isMember(group, to);

        Assertions.assertThrows(UserAccessDeniedException.class,
                () -> groupExhibitService.addManager(0L, 1L, 0L));
    }

    @DisplayName("Get Group Exhibit Users Test")
    @Test
    void getGroupExhibitUsersTest() {
        User user = User.builder().name("test").build();
        Member member = Member.builder().user(user).groupRole(GroupRole.MEMBER).build();

        GroupExhibit group = GroupExhibit.builder().build();
        group.addMember(member);

        given(groupExhibitRepository.findById(0L)).willReturn(Optional.of(group));
        given(userService.getUserById(0L)).willReturn(user);

        GroupExhibitUserResponses responses = groupExhibitService.getGroupExhibitUsers(0L, 0L);

        responses.getGroupExhibitUserResponses().forEach(
                groupMember -> Assertions.assertEquals("test", groupMember.getUserInfoResponse().getName())
        );
    }

    @DisplayName("Create Invitation Test")
    @Test
    void createInvitationTest() {
        GroupExhibit group = GroupExhibit.builder()
                .name("test")
                .build();

        given(groupExhibitRepository.findById(0L)).willReturn(Optional.of(group));

        GroupInvitationResponse response = groupExhibitService.createInvitation(0L);

        Assertions.assertEquals("test", response.getName());
    }
}
