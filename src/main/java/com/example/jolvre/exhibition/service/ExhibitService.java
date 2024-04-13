package com.example.jolvre.exhibition.service;

import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.exhibition.repository.ExhibitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExhibitService {
    private final ExhibitRepository exhibitRepository;

    public Exhibit upload(Exhibit exhibit) {
        return exhibitRepository.save(exhibit);
    }

    public Exhibit update(Exhibit exhibit) {
        return null;
    }

    public void delete(Exhibit exhibit) {
        exhibitRepository.delete(exhibit);
    }
}
