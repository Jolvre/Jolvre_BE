package com.example.jolvre.service.exhibition.impl;

import com.example.jolvre.domain.exhibition.Exhibit;
import com.example.jolvre.repository.exhibition.ExhibitRepository;
import com.example.jolvre.service.exhibition.ExhibitService;
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
