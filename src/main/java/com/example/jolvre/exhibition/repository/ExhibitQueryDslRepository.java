package com.example.jolvre.exhibition.repository;

import static com.example.jolvre.exhibition.entity.QDiary.diary;
import static com.example.jolvre.exhibition.entity.QExhibit.exhibit;
import static com.example.jolvre.exhibition.entity.QExhibitImage.exhibitImage;
import static com.example.jolvre.user.entity.QUser.user;

import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitInfoResponse;
import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.exhibition.entity.QExhibit;
import com.example.jolvre.user.entity.User;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ExhibitQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public List<Exhibit> findAllByFilter(boolean distribute, String title) {
        return queryFactory
                .selectFrom(exhibit)
                .where(exhibit.distribute.eq(distribute), containTitle(title))
                .orderBy(exhibit.id.asc())
                .fetch();
    }

    public Page<ExhibitInfoResponse> findAllByFilter(boolean distribute, String title, Pageable pageable) {
        List<ExhibitInfoResponse> exhibits = queryFactory
                .select(Projections.constructor(ExhibitInfoResponse.class
                        , QExhibit.exhibit.id
                        , QExhibit.exhibit.title
                        , QExhibit.exhibit.thumbnail
                ))
                .leftJoin(exhibit.user, user)
                .fetchJoin()
                .leftJoin(exhibit.exhibitImages, exhibitImage)
                .leftJoin(exhibit.diaries, diary)
                .where(QExhibit.exhibit.distribute.eq(distribute), containTitle(title))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(exhibit.count())
                .from(exhibit)
                .leftJoin(exhibit.user, user)
                .fetchJoin()
                .leftJoin(exhibit.exhibitImages, exhibitImage)
                .leftJoin(exhibit.diaries, diary)
                .where(exhibit.distribute.eq(distribute), containTitle(title));

        return PageableExecutionUtils.getPage(exhibits, pageable, count::fetchOne);
    }

    private BooleanExpression containTitle(String title) {
        if (title == null || title.isBlank()) {
            return null;
        }

        return exhibit.title.contains(title);
    }

    private BooleanExpression eqUser(User user) {
        if (user == null) {
            return null;
        }

        return exhibit.user.eq(user);
    }
}
