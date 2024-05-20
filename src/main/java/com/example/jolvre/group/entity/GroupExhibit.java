package com.example.jolvre.group.entity;

import com.example.jolvre.exhibition.entity.Exhibit;
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
public class GroupExhibit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_exhibit_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String period;

    @Column
    private String selectedItem;

    @Column
    private String introduction;

    @OneToMany(mappedBy = "groupExhibit")
    private List<Manager> managers = new ArrayList<>();

    @OneToMany(mappedBy = "groupExhibit")
    private List<Exhibit> exhibits = new ArrayList<>();

    @OneToMany(mappedBy = "groupExhibit")
    private List<Member> members = new ArrayList<>();

    @Builder
    public GroupExhibit(String name, String period, String selectedItem) {
        this.name = name;
        this.period = period;
        this.selectedItem = selectedItem;
    }

    public void addManger(Manager manager) {
        manager.setGroupExhibit(this);
        this.managers.add(manager);
    }

    public void addMember(Member member) {
        member.setGroupExhibit(this);
        this.members.add(member);
    }

    public void addExhibit(Exhibit exhibit) {
        exhibit.setGroupExhibit(this);
        this.exhibits.add(exhibit);
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

}
