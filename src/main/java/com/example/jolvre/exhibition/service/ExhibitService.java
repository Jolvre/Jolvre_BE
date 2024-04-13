package com.example.jolvre.exhibition.service;

import com.example.jolvre.exhibition.entity.Exhibit;

public interface ExhibitService {
    public Exhibit upload(Exhibit exhibit);

    public Exhibit update(Exhibit exhibit);

    public void delete(Exhibit exhibit);
}
