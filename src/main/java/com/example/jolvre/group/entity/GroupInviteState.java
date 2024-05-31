package com.example.jolvre.group.entity;

import com.example.jolvre.common.entity.BaseTimeEntity;
import com.example.jolvre.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupInviteState extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_invite_state")
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private InviteState inviteState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_exhibit_id")
    private GroupExhibit groupExhibit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public GroupInviteState(InviteState inviteState, GroupExhibit groupExhibit, User user) {
        this.inviteState = inviteState;
        this.groupExhibit = groupExhibit;
        this.user = user;
    }
}
