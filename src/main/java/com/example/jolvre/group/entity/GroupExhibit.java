package com.example.jolvre.group.entity;

import com.example.jolvre.common.entity.BaseTimeEntity;
import com.example.jolvre.common.error.user.UserNotFoundException;
import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupUpdateRequest;
import com.example.jolvre.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "group_exhibit")
public class GroupExhibit extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_exhibit_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String period; //개최기간

    @Column
    private String selectedItem; // 상품선택

    @Column(length = 1000)
    private String introduction; //소개

    @Column
    private String thumbnail;

    @OneToMany(mappedBy = "groupExhibit", orphanRemoval = true)
    private List<RegisteredExhibit> registeredExhibits = new ArrayList<>();

    @OneToMany(mappedBy = "groupExhibit", orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    @Builder
    public GroupExhibit(String name, String period, String selectedItem, String introduction, String thumbnail) {
        this.name = name;
        this.period = period;
        this.selectedItem = selectedItem;
        this.introduction = introduction;
        this.thumbnail = thumbnail;
    }
    
    public void addMember(Member member) {
        member.setGroupExhibit(this);
        this.members.add(member);
    }

    public void addExhibit(RegisteredExhibit exhibit) {
        exhibit.setGroupExhibit(this);
        this.registeredExhibits.add(exhibit);
    }

    public boolean checkManager(User user) {
        Member userMember = this.getMembers().stream()
                .filter(member -> Objects.equals(member.getUser().getId(), user.getId())).findFirst()
                .orElseThrow(UserNotFoundException::new);

        return userMember.getGroupRole() == GroupRole.MANAGER;
    }

    public boolean checkMember(User user) {
        Member userMember = this.getMembers().stream()
                .filter(member -> Objects.equals(member.getUser().getId(), user.getId())).findFirst()
                .orElseThrow(UserNotFoundException::new);

        return userMember.getGroupRole() == GroupRole.MEMBER;
    }

    public List<Exhibit> getRegisteredExhibitInfo() {
        List<Exhibit> exhibits = new ArrayList<>();

        this.registeredExhibits.forEach(
                registeredExhibit -> exhibits.add(registeredExhibit.getExhibit())
        );

        return exhibits;
    }

    public void update(GroupUpdateRequest request) {
        this.name = request.getName();
        this.introduction = request.getIntroduction();
    }

    public void updateThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;

    }
}
