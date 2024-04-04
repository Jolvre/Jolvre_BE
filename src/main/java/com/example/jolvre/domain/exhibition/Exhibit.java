package com.example.jolvre.domain.exhibition;

import com.example.jolvre.domain.BaseTimeEntity;
import com.example.jolvre.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalTime;
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

    @Column
    private String title;

    @Column
    private int up; //추천수

    @Column
    private LocalTime uploadDate;

    @Column
    private String authorWord; //작가의 한마디

    @Column
    private String introduction; //작품 소개

    @Column
    private String size;

    @Column
    private String productionMethod;

    @Column
    private int price;

    @Column
    private boolean salesStatus; // 판매여부

    public Exhibit(List<Diary> diaries, User user, List<ExhibitPicture> exhibitPictures) {
        this.diaries = diaries;
        this.user = user;
        this.exhibitPictures = exhibitPictures;
    }
}
