package com.example.graduate_minions.domain.exhibition;

import com.example.graduate_minions.domain.BaseTimeEntity;
import com.example.graduate_minions.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Exhibit extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exhibit_id")
    private Long id;


    @OneToMany // 1:N
    @JoinColumn(name = "diary_id")
    private List<Diary> diaries = new ArrayList<>();


    @ManyToOne // N:1
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany // 1:N
    @JoinColumn(name = "exhibit_picutre_id")
    private List<ExhibitPicture> exhibitPictures = new ArrayList<>();

    public Exhibit(List<Diary> diaries, User user, List<ExhibitPicture> exhibitPictures) {
        this.diaries = diaries;
        this.user = user;
        this.exhibitPictures = exhibitPictures;
    }
}
