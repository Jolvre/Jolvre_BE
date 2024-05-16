package com.example.jolvre.exhibition.entity;

import com.example.jolvre.common.entity.BaseTimeEntity;
import com.example.jolvre.group.entity.GroupExhibit;
import com.example.jolvre.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Exhibit extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exhibit_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY) // N:1
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String title;

    @Column
    private int up; //추천수

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
    private boolean forSale; // 판매 여부

    @Column
    private boolean distribute = false; // 배포 여부

    @Column
    private String thumbnail;

    @OneToMany(mappedBy = "exhibit", fetch = FetchType.LAZY)
    private List<ExhibitImage> exhibitImages = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_exhibit_id")
    private GroupExhibit groupExhibit;

    @Column
    private String image3d;

    @Builder
    public Exhibit(User user, String title, String authorWord, String introduction, String size,
                   String productionMethod, int price, boolean forSale, String thumbnail) {
        this.user = user;
        this.title = title;
        this.authorWord = authorWord;
        this.introduction = introduction;
        this.size = size;
        this.productionMethod = productionMethod;
        this.price = price;
        this.forSale = forSale;
        this.up = 0;
        this.distribute = false;
        this.thumbnail = thumbnail;
    }

    public void addImage(ExhibitImage exhibitImage) {
        exhibitImage.addExhibit(this);
        this.exhibitImages.add(exhibitImage);
    }

    public List<String> getImageUrls() {
        List<String> urls = new ArrayList<>();

        this.exhibitImages.forEach(
                image -> urls.add(image.getUrl())
        );

        return urls;
    }

    public void up() {
        this.up += 1;
    }

    public void startDistribute() {
        this.distribute = true;
    }
}
