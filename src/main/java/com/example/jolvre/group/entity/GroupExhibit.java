package com.example.jolvre.group.entity;

import com.example.jolvre.common.entity.BaseTimeEntity;
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
    private List<Manager> managers = new ArrayList<>(); //todo : 멤버 매니저 통합 , 멤버 Role 컬럼 생성

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

    public void addManger(Manager manager) {
        manager.setGroupExhibit(this);
        this.managers.add(manager);
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
        List<User> users = new ArrayList<>();

        this.getManagers().forEach(
                manager -> users.add(manager.getUser())
        );

        return users.contains(user);
    }

    public boolean checkMember(User user) {
        List<User> users = new ArrayList<>();

        this.getMembers().forEach(
                member -> users.add(member.getUser())
        );

        return users.contains(user);
    }

    public List<User> getMembersInfo() {
        List<User> users = new ArrayList<>();

        this.getMembers().forEach(
                member -> users.add(member.getUser())
        );

        return users;
    }

    public List<User> getManagersInfo() {
        List<User> users = new ArrayList<>();

        this.getManagers().forEach(
                member -> users.add(member.getUser())
        );

        return users;
    }

    public List<Exhibit> getRegisteredExhibitInfo() {
        List<Exhibit> exhibits = new ArrayList<>();

        this.registeredExhibits.forEach(
                registeredExhibit -> exhibits.add(registeredExhibit.getExhibit())
        );

        return exhibits;
    }

    public void update(GroupUpdateRequest request, String thumbnail) {
        this.name = request.getName();
        this.introduction = request.getIntroduction();

        this.thumbnail = thumbnail;
    }
}
