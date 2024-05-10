package com.example.jolvre.group.service;

import com.example.jolvre.common.error.group.GroupExhibitNotFoundException;
import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.exhibition.service.ExhibitService;
import com.example.jolvre.group.entity.GroupExhibit;
import com.example.jolvre.group.repository.GroupExhibitRepository;
import com.example.jolvre.group.repository.GroupInviteStateRepository;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupExhibitService {
    private final GroupExhibitRepository groupExhibitRepository;
    private final GroupInviteStateRepository groupInviteStateRepository;
    private final UserService userService;
    private final ExhibitService exhibitService;

    @Transactional
    public void createGroupExhibit(Long userId) {
        User manager = userService.getUserById(userId);

        GroupExhibit group = GroupExhibit.builder().manager(manager).build();

        groupExhibitRepository.save(group);

    }

    @Transactional
    public void addExhibit(Long groupId, Long exhibitId) {
        Exhibit exhibit = exhibitService.getExhibitById(exhibitId);

        GroupExhibit group = groupExhibitRepository.findById(groupId)
                .orElseThrow(GroupExhibitNotFoundException::new);

        group.addExhibit(exhibit);

        groupExhibitRepository.save(group);
    }

    @Transactional
    public void inviteUser() {

    }
}
