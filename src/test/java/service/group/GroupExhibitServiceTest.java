package service.group;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.exhibition.service.ExhibitService;
import com.example.jolvre.group.GroupRoleChecker;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupExhibitCreateRequest;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupExhibitInfoResponse;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupExhibitInfoResponses;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupExhibitUserResponses;
import com.example.jolvre.group.entity.GroupExhibit;
import com.example.jolvre.group.entity.Member;
import com.example.jolvre.group.repository.GroupExhibitRepository;
import com.example.jolvre.group.repository.MemberRepository;
import com.example.jolvre.group.service.GroupExhibitService;
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
public class GroupExhibitServiceTest {
    @Mock
    GroupExhibitRepository groupExhibitRepository;
    @Mock
    ManagerRepository managerRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    UserService userService;
    @Mock
    ExhibitService exhibitService;
    @Mock
    GroupRoleChecker checker;

    @InjectMocks
    GroupExhibitService groupExhibitService;

    @Test
    @DisplayName("Create Group Exhibit")
    void createGroupExhibit() {
        GroupExhibitCreateRequest request = GroupExhibitCreateRequest.builder()
                .name("test")
                .build();

        groupExhibitService.createGroupExhibit(0L, request);

        verify(groupExhibitRepository).save(any());
    }

    @Test
    @DisplayName("Get All Group Exhibit")
    void getAllGroupExhibit() {
        GroupExhibit test1 = GroupExhibit.builder().name("test1").build();
        GroupExhibit test2 = GroupExhibit.builder().name("test2").build();
        GroupExhibit test3 = GroupExhibit.builder().name("test3").build();

        List<GroupExhibit> groupExhibits = new ArrayList<>();
        groupExhibits.add(test1);
        groupExhibits.add(test2);
        groupExhibits.add(test3);

        given(groupExhibitRepository.findAll()).willReturn(groupExhibits);

        GroupExhibitInfoResponses responses = groupExhibitService.getAllGroupExhibitInfo();

        GroupExhibitInfoResponse group = responses.getGroupExhibitResponses().get(0);

        Assertions.assertEquals(test1.getName(), group.getName());
    }

    @Test
    @DisplayName("Get Group Exhibit")
    void getGroupExhibit() {
        GroupExhibit test = GroupExhibit.builder().name("test").build();

        given(groupExhibitRepository.findById(any())).willReturn(Optional.of(test));

        GroupExhibitInfoResponse response = groupExhibitService.getGroupExhibitInfo(0L);

        Assertions.assertEquals(test.getName(), response.getName());
    }

    @Test
    @DisplayName("Get All USer Group Exhibit")
    void getAllUserGroupExhibit() {
        GroupExhibit test = GroupExhibit.builder().name("test").build();
        Member member = Member.builder().groupExhibit(test).build();
        List<Member> members = new ArrayList<>();
        members.add(member);

        given(memberRepository.findAllByUserId(anyLong())).willReturn(members);

        GroupExhibitInfoResponses responses = groupExhibitService.getAllUserGroupExhibitInfo(0L);
        String name = responses.getGroupExhibitResponses().get(0).getName();

        Assertions.assertEquals(test.getName(), name);
    }

    @Test
    @DisplayName("Get Group Exhibit Users")
    void getGroupExhibitUsers() {

        Member member1 = Member.builder().user(User.builder().name("test1").build()).build();
        Member member2 = Member.builder().user(User.builder().name("test2").build()).build();
        Manager manager = Manager.builder().user(User.builder().name("test3").build()).build();

        GroupExhibit test = GroupExhibit.builder().name("test").build();
        test.addMember(member1);
        test.addMember(member2);
        test.addManger(manager);

        given(groupExhibitRepository.findById(any())).willReturn(Optional.of(test));

        GroupExhibitUserResponses responses = groupExhibitService.getGroupExhibitUsers(0L, 0L);
        String username = responses.getGroupExhibitUserResponses().get(0).getUserInfoResponse().getName();

        Assertions.assertEquals("test3", username);
    }

    @Test
    @DisplayName("Add Exhibit")
    void addExhibit() {
        GroupExhibit test = GroupExhibit.builder().name("test").build();
        Exhibit exhibit = Exhibit.builder().build();
        User user = User.builder().build();

        given(groupExhibitRepository.findById(anyLong())).willReturn(Optional.of(test));
        given(exhibitService.getExhibitById(anyLong())).willReturn(exhibit);
        given(userService.getUserById(anyLong())).willReturn(user);

        groupExhibitService.addExhibit(0L, 0L, 0L);

        verify(groupExhibitRepository).save(any());
    }

    @Test
    @DisplayName("Add Manager")
    void addManager() {
        User fromUser = User.builder().name("from").build();
        User toUser = User.builder().name("to").build();
        GroupExhibit test = GroupExhibit.builder().name("test").build();

        given(userService.getUserById(0L)).willReturn(fromUser);
        given(userService.getUserById(1L)).willReturn(toUser);
        given(groupExhibitRepository.findById(anyLong())).willReturn(Optional.of(test));

        groupExhibitService.addManager(0L, 1L, 0L);

        verify(groupExhibitRepository).save(any());
    }
}
