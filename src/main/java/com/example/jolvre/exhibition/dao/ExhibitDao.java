package com.example.jolvre.exhibition.dao;

import static com.example.jolvre.exhibition.entity.QExhibit.exhibit;

import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.user.entity.QUser;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ExhibitDao {
    private final JPAQueryFactory queryFactory;

    public List<Exhibit> findAllByFilter(boolean distribute, String title) {
        return queryFactory
                .selectFrom(exhibit)
                .where(exhibit.distribute.eq(distribute), eqTitle(title))
                .leftJoin(exhibit.user, QUser.user)
                .fetchJoin()
                .orderBy(exhibit.id.asc())
                .fetch();
    }

    private BooleanExpression eqTitle(String title) {
        if (title == null || title.isBlank()) {
            return null;
        }

        return exhibit.title.contains(title);
    }
}
