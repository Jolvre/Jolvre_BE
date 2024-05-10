package com.example.jolvre.group.entity;

import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

    @OneToOne
    @JoinColumn(name = "user_id")
    private User manager;

    @OneToMany(mappedBy = "groupExhibit")
    private List<Exhibit> exhibits = new ArrayList<>();

    @OneToMany(mappedBy = "groupExhibit")
    private List<User> users = new ArrayList<>();

    @Builder
    public GroupExhibit(User manager) {
        this.manager = manager;
    }

    public void addUser(User user) {
        user.setGroupExhibit(this);
        this.users.add(user);
    }

    public void addExhibit(Exhibit exhibit) {
        exhibit.setGroupExhibit(this);
        this.exhibits.add(exhibit);
    }
}
