package com.example.jolvre.exhibition.service.impl;

import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.exhibition.repository.ExhibitRepository;
import com.example.jolvre.exhibition.service.ExhibitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExhibitServiceImpl implements ExhibitService {
    private final ExhibitRepository exhibitRepository;

    @Override
    public Exhibit upload(Exhibit exhibit) {
        return exhibitRepository.save(exhibit);
    }

    @Override
    public Exhibit update(Exhibit exhibit) {
        return null;
    }

    @Override
    public void delete(Exhibit exhibit) {
        exhibitRepository.delete(exhibit);
    }
}
