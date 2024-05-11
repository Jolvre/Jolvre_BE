package com.example.jolvre.group.service;

import com.example.jolvre.common.error.group.GroupExhibitNotFoundException;
import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.exhibition.service.ExhibitService;
import com.example.jolvre.group.dto.GroupInviteDTO.InviteResponses;
import com.example.jolvre.group.entity.GroupExhibit;
import com.example.jolvre.group.entity.GroupInviteState;
import com.example.jolvre.group.entity.InviteState;
import com.example.jolvre.group.entity.Manager;
import com.example.jolvre.group.entity.Member;
import com.example.jolvre.group.repository.GroupExhibitRepository;
import com.example.jolvre.group.repository.GroupInviteStateRepository;
import com.example.jolvre.group.repository.ManagerRepository;
import com.example.jolvre.group.repository.MemberRepository;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.service.UserService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupExhibitService {
    private final GroupExhibitRepository groupExhibitRepository;
    private final GroupInviteStateRepository groupInviteStateRepository;
    private final ManagerRepository managerRepository;
    private final MemberRepository memberRepository;
    private final UserService userService;
    private final ExhibitService exhibitService;

    @Transactional //단체 전시 생성
    public void createGroupExhibit(Long userId) {
        User loginUser = userService.getUserById(userId);
        Manager manager = Manager.builder().user(loginUser).build();
        Member member = Member.builder().user(loginUser).build();

        managerRepository.save(manager);
        memberRepository.save(member);

        GroupExhibit group = new GroupExhibit();

        group.addManger(manager);
        group.addMember(member);

        groupExhibitRepository.save(group);

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

    @Transactional // 유저 초대
    public void inviteUser(Long fromUser, Long toUser, Long groupId) {
        User from = userService.getUserById(fromUser);
        User to = userService.getUserById(toUser);

        GroupExhibit group = groupExhibitRepository.findById(groupId)
                .orElseThrow(GroupExhibitNotFoundException::new);
        group.checkManager(from);

        GroupInviteState inviteState = GroupInviteState.builder()
                .groupExhibit(group)
                .inviteState(InviteState.PEND)
                .user(to).build();

        groupInviteStateRepository.save(inviteState);
    }

    @Transactional
    public InviteResponses getAllInvite(Long loginUserId) {
        List<GroupInviteState> invites = groupInviteStateRepository.findAllByUserId(loginUserId);

        return InviteResponses.toDTO(invites);
    }

    @Transactional
    public void checkInviteStatus(Long inviteId, String inviteState) {
        GroupInviteState invite = groupInviteStateRepository.findById(inviteId)
                .orElseThrow(GroupExhibitNotFoundException::new);
        GroupExhibit group = invite.getGroupExhibit();
        User user = invite.getUser();

        if (InviteState.ACCEPT.toString().equals(inviteState)) {
            acceptInviteStatus(invite, user, group);
            return;
        }

        refuseInviteStatus(invite);
    }

    @Transactional
    public void acceptInviteStatus(GroupInviteState invite, User user, GroupExhibit group) {
        groupInviteStateRepository.delete(invite);
        Member member = Member.builder().user(user).groupExhibit(group).build();
        memberRepository.save(member);

        group.addMember(member);
        groupExhibitRepository.save(group);
    }

    @Transactional
    public void refuseInviteStatus(GroupInviteState invite) {
        groupInviteStateRepository.delete(invite);
    }

}
