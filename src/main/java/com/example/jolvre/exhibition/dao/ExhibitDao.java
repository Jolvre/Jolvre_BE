package com.example.jolvre.exhibition.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ExhibitDao {
    private final JPAQueryFactory queryFactory;
    
}
