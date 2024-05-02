package com.example.jolvre.exhibition.entity;

import com.example.jolvre.common.entity.Image;
import jakarta.persistence.Entity;
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
public class ExhibitImage extends Image {

    @ManyToOne
    @JoinColumn(name = "exhibit_id")
    private Exhibit exhibit;

    @Builder
    public ExhibitImage(String url, Exhibit exhibit) {
        super(url);
        this.exhibit = exhibit;
    }
}
