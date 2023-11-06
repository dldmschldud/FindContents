package com.shop.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.shop.domain.QTheme;
import com.shop.domain.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ThemeSearchImpl extends QuerydslRepositorySupport implements ThemeSearch {
    public ThemeSearchImpl() {super(Theme.class);}

    @Override
    public Page<Theme> searchAll(String[] types, String keyword, Pageable pageable) {
        QTheme theme = QTheme.theme;
        JPQLQuery<Theme> query = from(theme);
        if( (types != null && types.length > 0) && keyword !=null){
            //검색 조건과 키워드가 있다면
            BooleanBuilder booleanBuilder = new BooleanBuilder(); //(
            for(String type:types){
                switch (type){
                    case "a":
                        booleanBuilder.or(theme.thTitle.contains(keyword));
                        booleanBuilder.or(theme.thExplanation.contains(keyword));
                        booleanBuilder.or(theme.thWriter.contains(keyword));
                        break;
                    case "t":
                        booleanBuilder.or(theme.thTitle.contains(keyword));
                        break;
                    case "e":
                        booleanBuilder.or(theme.thExplanation.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(theme.thWriter.contains(keyword));
                        break;
                }
            }//end for
            query.where(booleanBuilder);
        }
        query.where(theme.tid.gt(0L));
        this.getQuerydsl().applyPagination(pageable, query);
        List<Theme> list = query.fetch();
        long count = query.fetchCount();
        return new PageImpl<>(list, pageable, count);
    }
}
