package com.example.jolvre.group.service;

import com.example.jolvre.common.error.group.GroupExhibitNotFoundException;
import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.exhibition.service.ExhibitService;
import com.example.jolvre.group.GroupRoleChecker;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupExhibitCreateRequest;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupExhibitResponse;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupExhibitResponses;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupExhibitUserResponse;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupExhibitUserResponses;
import com.example.jolvre.group.entity.GroupExhibit;
import com.example.jolvre.group.entity.Manager;
import com.example.jolvre.group.entity.Member;
import com.example.jolvre.group.repository.GroupExhibitRepository;
import com.example.jolvre.group.repository.ManagerRepository;
import com.example.jolvre.group.repository.MemberRepository;
import com.example.jolvre.user.dto.UserDTO.UserInfoResponse;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.service.UserService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupExhibitService {
    private final GroupExhibitRepository groupExhibitRepository;
    private final ManagerRepository managerRepository;
    private final MemberRepository memberRepository;
    private final UserService userService;
    private final ExhibitService exhibitService;
    private final GroupRoleChecker checker;


    @Transactional //단체 전시 생성
    public void createGroupExhibit(Long userId, GroupExhibitCreateRequest request) {
        User loginUser = userService.getUserById(userId);
        Manager manager = Manager.builder().user(loginUser).build();
        Member member = Member.builder().user(loginUser).build();

        managerRepository.save(manager);
        memberRepository.save(member);

        GroupExhibit group = GroupExhibit.builder()
                .name(request.getName())
                .build();

        group.addManger(manager);
        group.addMember(member);

        groupExhibitRepository.save(group);
    }

    @Transactional //모든 단체전시 탭에서 조회
    public GroupExhibitResponses getAllGroupExhibit() { //페이징 필요
        List<GroupExhibit> groupExhibits = groupExhibitRepository.findAll();

        return GroupExhibitResponses.toDTO(groupExhibits);
    }

    @Transactional //단체전시 상세 조회
    public GroupExhibitResponse getGroupExhibit(Long groupId) {
        GroupExhibit group = groupExhibitRepository.findById(groupId)
                .orElseThrow(GroupExhibitNotFoundException::new);

        return GroupExhibitResponse.toDTO(group);
    }

    @Transactional //유저 탭에서 모든 단체전시 조회
    public GroupExhibitResponses getAllUserGroupExhibit(Long userId) {
        List<Member> members = memberRepository.findAllByUserId(userId);

        List<GroupExhibit> groupExhibits = new ArrayList<>();

        members.forEach(member -> groupExhibits.add(member.getGroupExhibit()));

        return GroupExhibitResponses.toDTO(groupExhibits);
    }

    @Transactional // 유저가 단체 전시에 전시 추가
    public void addExhibit(Long userId, Long groupId, Long exhibitId) {
        User user = userService.getUserById(userId);

        GroupExhibit group = groupExhibitRepository.findById(groupId)
                .orElseThrow(GroupExhibitNotFoundException::new);

        group.checkMember(user);

        Exhibit exhibit = exhibitService.getExhibitById(exhibitId);

        group.addExhibit(exhibit);

        groupExhibitRepository.save(group);
    }

    @Transactional //단체전시 회원 조회
    public GroupExhibitUserResponses getGroupExhibitUsers(Long groupId) {
        GroupExhibit group = groupExhibitRepository.findById(groupId)
                .orElseThrow(GroupExhibitNotFoundException::new);

        List<User> members = group.getMembersInfo();
        List<User> managers = group.getManagersInfo();
        List<GroupExhibitUserResponse> groupUserInfo = new ArrayList<>();

        managers.forEach(
                user -> groupUserInfo.add(GroupExhibitUserResponse.builder()
                        .role("MANAGER")
                        .userInfoResponse(UserInfoResponse.toDTO(user))
                        .build())
        );

        members.stream().filter(user -> !managers.contains(user))
                .forEach(user -> groupUserInfo.add(GroupExhibitUserResponse.builder()
                        .role("MEMEBER")
                        .userInfoResponse(UserInfoResponse.toDTO(user))
                        .build()));

        return GroupExhibitUserResponses.builder()
                .groupExhibitUserResponses(groupUserInfo)
                .build();
    }

    @Transactional // 매니처 추가
    public void addManager(Long fromUser, Long toUser, Long groupId) {
        User from = userService.getUserById(fromUser);
        User to = userService.getUserById(toUser);
        GroupExhibit group = groupExhibitRepository.findById(groupId)
                .orElseThrow(GroupExhibitNotFoundException::new);

        checker.isManager(group, from); // 초대 보내는 사람 -> 매니저
        checker.isMember(group, to); // 초대 받는 사람 -> 멤버

        Manager manager = Manager.builder()
                .groupExhibit(group)
                .user(to).build();

        managerRepository.save(manager);
        group.addManger(manager);
        groupExhibitRepository.save(group);
    }
}
