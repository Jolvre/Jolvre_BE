package com.example.jolvre.group.service;

import com.example.jolvre.common.error.group.GroupExhibitNotFoundException;
import com.example.jolvre.common.service.S3Service;
import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.exhibition.service.ExhibitService;
import com.example.jolvre.group.GroupRoleChecker;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupExhibitCreateRequest;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupExhibitInfoResponse;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupExhibitInfoResponses;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupExhibitUserResponse;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupExhibitUserResponses;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupInvitationResponse;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupUpdateRequest;
import com.example.jolvre.group.entity.GroupExhibit;
import com.example.jolvre.group.entity.Manager;
import com.example.jolvre.group.entity.Member;
import com.example.jolvre.group.entity.RegisteredExhibit;
import com.example.jolvre.group.repository.GroupExhibitRepository;
import com.example.jolvre.group.repository.ManagerRepository;
import com.example.jolvre.group.repository.MemberRepository;
import com.example.jolvre.group.repository.RegisteredExhibitRepository;
import com.example.jolvre.user.dto.UserDTO.UserInfoResponse;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.service.UserService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupExhibitService {
    private final GroupExhibitRepository groupExhibitRepository;
    private final ManagerRepository managerRepository;
    private final MemberRepository memberRepository;
    private final RegisteredExhibitRepository registeredExhibitRepository;
    private final UserService userService;
    private final ExhibitService exhibitService;
    private final GroupRoleChecker checker;
    private final S3Service s3Service;


    @Transactional //단체 전시 생성
    public void createGroupExhibit(Long userId, GroupExhibitCreateRequest request) {
        User loginUser = userService.getUserById(userId);
        Manager manager = Manager.builder().user(loginUser).build();
        Member member = Member.builder().user(loginUser).build();

        managerRepository.save(manager);
        memberRepository.save(member);

        String thumbnail = s3Service.uploadImage(request.getThumbnail());

        GroupExhibit group = GroupExhibit.builder()
                .name(request.getName())
                .period(request.getPeriod())
                .selectedItem(request.getSelectedItem())
                .introduction(request.getIntroduction())
                .thumbnail(thumbnail)
                .build();

        group.addManger(manager);
        group.addMember(member);

        groupExhibitRepository.save(group);
    }

    @Transactional //모든 단체전시 탭에서 조회
    public GroupExhibitInfoResponses getAllGroupExhibitInfo() { //페이징 필요
        List<GroupExhibit> groupExhibits = groupExhibitRepository.findAll();

        return GroupExhibitInfoResponses.toDTO(groupExhibits);
    }

    @Transactional //단체전시 상세 조회
    public GroupExhibitInfoResponse getGroupExhibitInfo(Long groupId) {
        GroupExhibit group = groupExhibitRepository.findById(groupId)
                .orElseThrow(GroupExhibitNotFoundException::new);

        return GroupExhibitInfoResponse.toDTO(group);
    }

    @Transactional //유저 탭에서 모든 단체전시 조회
    public GroupExhibitInfoResponses getAllUserGroupExhibitInfo(Long userId) {
        List<Member> members = memberRepository.findAllByUserId(userId);

        List<GroupExhibit> groupExhibits = new ArrayList<>();

        members.forEach(member -> groupExhibits.add(member.getGroupExhibit()));

        return GroupExhibitInfoResponses.toDTO(groupExhibits);
    }

    @Transactional // 유저가 단체 전시에 전시 추가
    public void addExhibit(Long userId, Long groupId, Long exhibitId) {
        User user = userService.getUserById(userId);

        GroupExhibit group = groupExhibitRepository.findById(groupId)
                .orElseThrow(GroupExhibitNotFoundException::new);

        group.checkMember(user);

        Exhibit exhibit = exhibitService.getExhibitById(exhibitId);

        RegisteredExhibit registeredExhibit = RegisteredExhibit.builder()
                .exhibit(exhibit).build();

        registeredExhibitRepository.save(registeredExhibit);
        group.addExhibit(registeredExhibit);
        groupExhibitRepository.save(group);
    }

    @Transactional
    public void deleteGroup(Long groupId, Long loginUserId) {
        User loginUser = userService.getUserById(loginUserId);
        GroupExhibit group = groupExhibitRepository.findById(groupId)
                .orElseThrow(GroupExhibitNotFoundException::new);

        checker.isManager(group, loginUser);

        group.getManagers().forEach(manager -> manager.setGroupExhibit(null));
        group.getMembers().forEach(member -> member.setGroupExhibit(null));
        group.getRegisteredExhibits().forEach(exhibit -> exhibit.setGroupExhibit(null));

        groupExhibitRepository.delete(group);
    }

    @Transactional //단체전시 회원 조회
    public GroupExhibitUserResponses getGroupExhibitUsers(Long loginUserId, Long groupId) {
        User loginUser = userService.getUserById(loginUserId);

        GroupExhibit group = groupExhibitRepository.findById(groupId)
                .orElseThrow(GroupExhibitNotFoundException::new);

        checker.isMember(group, loginUser);

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

    @Transactional
    public void updateGroup(Long groupId, Long loginUserId, GroupUpdateRequest request) {
        User loginUser = userService.getUserById(loginUserId);

        GroupExhibit group = groupExhibitRepository.findById(groupId)
                .orElseThrow(GroupExhibitNotFoundException::new);

        checker.isMember(group, loginUser);

        String thumbnail = s3Service.updateImage(request.getThumbnail(), group.getThumbnail());

        group.update(request, thumbnail);

        groupExhibitRepository.save(group);
    }

    @Transactional
    public GroupInvitationResponse createInvitation(Long groupId) {
        GroupExhibit group = groupExhibitRepository.findById(groupId)
                .orElseThrow(GroupExhibitNotFoundException::new);

        return GroupInvitationResponse.builder()
                .name(group.getName())
                .thumbnail(group.getThumbnail())
                .introduction(group.getIntroduction())
                .build();

    }

    public Page<GroupExhibitInfoResponse> getExhibitInfoByKeyword(String keyword, Pageable pageable) {
        if (keyword == null) {
            return groupExhibitRepository.findAll(pageable).map(GroupExhibitInfoResponse::toDTO);
        }
        return groupExhibitRepository.findByNameContaining(keyword, pageable).map(GroupExhibitInfoResponse::toDTO);
    }
}
