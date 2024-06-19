package com.example.jolvre.group;

import com.example.jolvre.common.error.user.UserAccessDeniedException;
import com.example.jolvre.group.entity.GroupExhibit;
import com.example.jolvre.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GroupRoleChecker {

    public void isMember(GroupExhibit group, User user) {
        if (!group.checkMember(user) && !group.checkManager(user)) {
            throw new UserAccessDeniedException();
        }
    }

    public void isNotMember(GroupExhibit group, User user) {
        if (group.checkMember(user) || group.checkManager(user)) {
            throw new UserAccessDeniedException();
        }
    }

    public void isManager(GroupExhibit group, User user) {
        if (!group.checkManager(user)) {
            throw new UserAccessDeniedException();
        }
    }

    public void isNotManager(GroupExhibit group, User user) {
        if (group.checkManager(user)) {
            throw new UserAccessDeniedException();
        }
    }
}
