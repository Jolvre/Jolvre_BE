package com.example.jolvre.exhibition.entity;

import com.example.jolvre.common.entity.BaseTimeEntity;
import com.example.jolvre.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
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


    @ManyToOne // N:1
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
    private boolean forSale; // 판매여부

    @Column
    private boolean distribute;

    @Column
    private String thumbnail;

    @Builder
    public Exhibit(User user, String title, String authorWord, String introduction, String size,
                   String productionMethod, int price, boolean forSale) {
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
    }

    public void up() {
        this.up += 1;
    }

    public void distribute() {
        this.distribute = true;
    }
}
