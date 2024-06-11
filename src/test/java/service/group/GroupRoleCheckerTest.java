package service.group;

import com.example.jolvre.common.error.user.UserAccessDeniedException;
import com.example.jolvre.group.GroupRoleChecker;
import com.example.jolvre.group.entity.GroupExhibit;
import com.example.jolvre.group.entity.GroupRole;
import com.example.jolvre.group.entity.Member;
import com.example.jolvre.user.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GroupRoleCheckerTest {
    GroupRoleChecker groupRoleChecker = new GroupRoleChecker();

    GroupExhibit group = new GroupExhibit();

    User memberUser = new User();
    User managerUser = new User();
    User user = new User();

    Member member;
    Member manager;

    @BeforeEach
    void init() {

        memberUser.setId(0L);
        managerUser.setId(1L);
        user.setId(2L);

        this.member = Member.builder()
                .user(memberUser)
                .groupRole(GroupRole.MEMBER)
                .build();

        this.manager = Member.builder()
                .user(managerUser)
                .groupRole(GroupRole.MANAGER)
                .build();

        group.addMember(member);
        group.addMember(manager);

    }

    @DisplayName("Is Member Test (Member , Manager)")
    @Test
    void isMemberTest() {
        groupRoleChecker.isMember(group, memberUser);
        groupRoleChecker.isMember(group, managerUser);
    }

    @DisplayName("Is Member Exception Test (User)")
    @Test
    void isMemberExceptionTest() {
        Assertions.assertThrows(UserAccessDeniedException.class,
                () -> groupRoleChecker.isMember(group, user));
    }

    @DisplayName("Is Not Member Test (User)")
    @Test
    void isNotMemberTest() {
        groupRoleChecker.isNotMember(group, user);
    }

    @DisplayName("Is Not Member Exception Test (Member , Manager)")
    @Test
    void isNotMemberExceptionTest() {
        Assertions.assertThrows(UserAccessDeniedException.class,
                () -> groupRoleChecker.isNotMember(group, memberUser));

        Assertions.assertThrows(UserAccessDeniedException.class,
                () -> groupRoleChecker.isNotMember(group, managerUser));

    }

    @DisplayName("Is Manager Test (Manager)")
    @Test
    void isManagerTest() {
        groupRoleChecker.isManager(group, managerUser);
    }

    @DisplayName("Is Manager Exception Test (Member , User)")
    @Test
    void isManagerExceptionTest() {
        Assertions.assertThrows(UserAccessDeniedException.class,
                () -> groupRoleChecker.isManager(group, memberUser));

        Assertions.assertThrows(UserAccessDeniedException.class,
                () -> groupRoleChecker.isManager(group, user));
    }

    @DisplayName("Is Not Manager Test (Member , User)")
    @Test
    void isNotManagerTest() {
        groupRoleChecker.isNotManager(group, memberUser);
        groupRoleChecker.isNotManager(group, user);
    }

    @DisplayName("Is Not Manager Exception Test (Manager)")
    @Test
    void isNotManagerExceptionTest() {
        Assertions.assertThrows(UserAccessDeniedException.class,
                () -> groupRoleChecker.isNotManager(group, managerUser));
    }
}

