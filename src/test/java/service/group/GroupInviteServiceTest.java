//package service.group;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//import com.example.jolvre.common.error.user.UserAccessDeniedException;
//import com.example.jolvre.group.GroupRoleChecker;
//import com.example.jolvre.group.dto.GroupInviteDTO.InviteResponses;
//import com.example.jolvre.group.entity.GroupExhibit;
//import com.example.jolvre.group.entity.GroupInviteState;
//import com.example.jolvre.group.entity.InviteState;
//import com.example.jolvre.group.entity.Manager;
//import com.example.jolvre.group.entity.Member;
//import com.example.jolvre.group.repository.GroupExhibitRepository;
//import com.example.jolvre.group.repository.GroupInviteStateRepository;
//import com.example.jolvre.group.repository.MemberRepository;
//import com.example.jolvre.group.service.GroupInviteService;
//import com.example.jolvre.user.entity.User;
//import com.example.jolvre.user.service.UserService;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Spy;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//@ExtendWith(MockitoExtension.class)
//public class GroupInviteServiceTest {
//
//    @Mock
//    UserService userService;
//    @Mock
//    GroupExhibitRepository groupExhibitRepository;
//    @Mock
//    GroupInviteStateRepository groupInviteStateRepository;
//    @Mock
//    MemberRepository memberRepository;
//    @Spy
//    GroupRoleChecker checker;
//
//    @InjectMocks
//    GroupInviteService groupInviteService;
//
//    @Test
//    @DisplayName("Invite User Test")
//    void inviteUserTest() {
//        User from = User.builder().build();
//        User to = User.builder().build();
//
//        GroupExhibit group = GroupExhibit.builder().name("test").build();
//        group.addMember(Member.builder().user(from).build());
//        group.addManger(Manager.builder().user(from).build());
//
//        given(userService.getUserById(0L)).willReturn(from);
//        given(userService.getUserByNickname("TEST")).willReturn(to);
//        given(groupExhibitRepository.findById(any())).willReturn(Optional.of(group));
//
//        groupInviteService.inviteUser(0L, "TEST", 0L);
//
//        verify(groupInviteStateRepository).save(any());
//    }
//
//    @Test
//    @DisplayName("Invite User Exception (to is Member) Test")
//    void inviteUserToIsMemberExceptionTest() {
//        User from = User.builder().build();
//        User to = User.builder().build();
//
//        GroupExhibit group = GroupExhibit.builder().name("test").build();
//
//        group.addMember(Member.builder().user(from).build());
//        group.addManger(Manager.builder().user(from).build());
//        group.addMember(Member.builder().user(to).build());
//
//        given(userService.getUserById(0L)).willReturn(from);
//        given(userService.getUserByNickname("TEST")).willReturn(to);
//        given(groupExhibitRepository.findById(any())).willReturn(Optional.of(group));
//
//        Assertions.assertThrows(UserAccessDeniedException.class, () ->
//                groupInviteService.inviteUser(0L, "TEST", 0L));
//    }
//
//    @Test
//    @DisplayName("Invite User Exception (from is not Manager) Test")
//    void inviteUserFromIsNotManagerExceptionTest() {
//        User from = User.builder().build();
//        User to = User.builder().build();
//
//        GroupExhibit group = GroupExhibit.builder().name("test").build();
//        group.addMember(Member.builder().user(from).build());
//
//        given(userService.getUserById(0L)).willReturn(from);
//        given(userService.getUserByNickname("TEST")).willReturn(to);
//        given(groupExhibitRepository.findById(any())).willReturn(Optional.of(group));
//
//        Assertions.assertThrows(UserAccessDeniedException.class, () ->
//                groupInviteService.inviteUser(0L, "TEST", 0L));
//    }
//
//    @Test
//    @DisplayName("Get All Invite Test")
//    void getAllInviteTest() {
//        GroupInviteState test = GroupInviteState.builder()
//                .groupExhibit(new GroupExhibit())
//                .user(new User())
//                .inviteState(InviteState.ACCEPT).build();
//
//        List<GroupInviteState> tests = new ArrayList<>();
//        tests.add(test);
//
//        given(groupInviteStateRepository.findAllByUserId(any())).willReturn(tests);
//
//        InviteResponses responses = groupInviteService.getAllInvite(0L);
//        String state = responses.getInviteResponses().get(0).getInviteState();
//
//        Assertions.assertEquals(InviteState.ACCEPT.toString(), state);
//    }
//
//    @Test
//    @DisplayName("Check Invite Status Test (Accept)")
//    void checkInviteStatusAcceptTest() {
//        GroupInviteState test = GroupInviteState.builder()
//                .inviteState(InviteState.PEND)
//                .user(new User())
//                .groupExhibit(new GroupExhibit())
//                .build();
//
//        given(groupInviteStateRepository.findById(any())).willReturn(Optional.of(test));
//
//        groupInviteService.checkInviteStatus(0L, InviteState.ACCEPT.toString());
//
//        verify(groupExhibitRepository).save(any());
//    }
//
//    @Test
//    @DisplayName("Check Invite Status Test (Refuse)")
//    void checkInviteStatusRefuseTest() {
//        GroupInviteState test = GroupInviteState.builder()
//                .inviteState(InviteState.PEND)
//                .user(new User())
//                .groupExhibit(new GroupExhibit())
//                .build();
//
//        given(groupInviteStateRepository.findById(any())).willReturn(Optional.of(test));
//
//        groupInviteService.checkInviteStatus(0L, InviteState.REFUSE.toString());
//
//        verify(groupExhibitRepository, times(0)).save(any());
//    }
//}
