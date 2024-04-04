package com.example.jolvre.service.exhibition;

import com.example.jolvre.domain.exhibition.Exhibit;

public interface ExhibitService {
    public Exhibit upload(Exhibit exhibit);

    public Exhibit update(Exhibit exhibit);

    public void delete(Exhibit exhibit);
}
