package com.shop.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.shop.domain.Contents;
import com.shop.domain.QContents;
import com.shop.domain.QReply;
import com.shop.dto.ContentsImageDTO;
import com.shop.dto.ContentsListAllDTO;
import com.shop.dto.ContentsListReplyCountDTO;
import com.shop.dto.ContentsOttDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

public class ContentsSearchImpl extends QuerydslRepositorySupport implements ContentsSearch {

    public ContentsSearchImpl(){
        super(Contents.class);
    }

    @Override
    public Page<Contents> search1(Pageable pageable) {

        QContents contents = QContents.contents;
        JPQLQuery<Contents> query = from(contents);
        query.where(contents.title.contains("1"));
        this.getQuerydsl().applyPagination(pageable,query);
        List<Contents> list = query.fetch();
        long count = query.fetchCount();
        return null;
    }

    @Override
    public Page<Contents> searchAll(String[] types, String keyword, Pageable pageable) {
        QContents contents = QContents.contents;
        JPQLQuery<Contents> query = from(contents);

        if( (types != null && types.length > 0) && keyword !=null){
            //검색 조건과 키워드가 있다면
            BooleanBuilder booleanBuilder = new BooleanBuilder(); //(
            for(String type:types){
                switch (type){
                    case "t":
                        booleanBuilder.or(contents.title.contains(keyword));//union
                        break;
                    case "e":
                        booleanBuilder.or(contents.explanation.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(contents.writer.contains(keyword));
                        break;
                }
            }//end for
            query.where(booleanBuilder);
        }//end if
        //id>0
        query.where(contents.id.gt(0L));//greater than
        //paging

        this.getQuerydsl().applyPagination(pageable, query);
        List<Contents> list = query.fetch();
        long count = query.fetchCount();
        return new PageImpl<>(list,pageable,count);
    }

    @Override
    public Page<ContentsListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {
        QContents contents = QContents.contents;
        QReply reply = QReply.reply;
        JPQLQuery<Contents> query = from(contents);
        query.leftJoin(reply).on(reply.contents.eq(contents));
        query.groupBy(contents);

        if ((types != null && types.length > 0) && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder(); //(
            for (String type : types) {
                switch (type) {
                    case "t":
                        booleanBuilder.or(contents.title.contains(keyword));
                        break;
                    case "e":
                        booleanBuilder.or(contents.explanation.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(contents.writer.contains(keyword));
                        break;

                }
            }//end for
            query.where(booleanBuilder);
        }
        //id>0
        query.where(contents.id.gt(0L));

        JPQLQuery<ContentsListReplyCountDTO> dtoQuery = query.select(Projections.
                bean(ContentsListReplyCountDTO.class,
                        contents.id,
                        contents.title,
                        contents.writer,
                        contents.regDate,
                        reply.count().as("replyCount")));

        this.getQuerydsl().applyPagination(pageable, dtoQuery);
        List<ContentsListReplyCountDTO> dtoList = dtoQuery.fetch();
        long count = dtoQuery.fetchCount();
        return new PageImpl<>(dtoList, pageable, count);
    }
    @Override
    public Page<ContentsListAllDTO> searchWithAll(String[] types, String keyword, Pageable pageable) {

        QContents contents = QContents.contents;
        QReply reply = QReply.reply;

        JPQLQuery<Contents> contentsJPQLQuery = from(contents);
        contentsJPQLQuery.leftJoin(reply).on(reply.contents.eq(contents));

        if((types !=null && types.length>0) && keyword !=null){
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for(String type:types){
                switch(type){
                    case "a":
                        booleanBuilder.or(contents.title.contains(keyword));
                        booleanBuilder.or(contents.explanation.contains(keyword));
                        booleanBuilder.or(contents.writer.contains(keyword));
                        break;
                    case "t":
                        booleanBuilder.or(contents.title.contains(keyword));
                        break;
                    case "e":
                        booleanBuilder.or(contents.explanation.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(contents.writer.contains(keyword));
                        break;

                }
            }//end for
            contentsJPQLQuery.where(booleanBuilder);
        }

        contentsJPQLQuery.groupBy(contents);
        getQuerydsl().applyPagination(pageable, contentsJPQLQuery);

        JPQLQuery<Tuple> tupleJPQLQuery = contentsJPQLQuery.select(contents, reply.countDistinct());
        List<Tuple> tupleList = tupleJPQLQuery.fetch();

        List<ContentsListAllDTO> dtoList = tupleList.stream().map(tuple -> {
            Contents contents1 = (Contents) tuple.get(contents);
            long replyCount = tuple.get(1, Long.class);

            ContentsListAllDTO dto = ContentsListAllDTO.builder()
                    .id(contents1.getId())
                    .title(contents1.getTitle())
                    .writer(contents1.getWriter())
                    .category(contents1.getCategory())
                    .cType(contents1.getCType())
                    .regDate(contents1.getRegDate())
                    .replyCount(replyCount)
                    .build();

            List<ContentsImageDTO> imageDTOs = contents1.getImageSet().stream().sorted()
                    .map(contentsImage -> ContentsImageDTO.builder()
                            .uuid(contentsImage.getUuid())
                            .fileName(contentsImage.getFileName())
                            .ord(contentsImage.getOrd())
                            .build()
                    ).collect(Collectors.toList());
            dto.setContentsImages(imageDTOs);
            return dto;
        }).collect(Collectors.toList());
        long totalCount = contentsJPQLQuery.fetchCount();
        return new PageImpl<>(dtoList, pageable, totalCount);

    }
    @Override
    public Page<ContentsListAllDTO> searchRomanceAll(String genre, String[] types, String keyword, Pageable pageable) {

        QContents contents = QContents.contents;
        QReply reply = QReply.reply;

        JPQLQuery<Contents> contentsJPQLQuery = from(contents);
        contentsJPQLQuery.leftJoin(reply).on(reply.contents.eq(contents));

        if((types !=null && types.length>0) && keyword !=null){
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for(String type:types){
                switch(type){
                    case "t":
                        booleanBuilder.or(contents.title.contains(keyword));
                        break;
                    case "e":
                        booleanBuilder.or(contents.explanation.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(contents.writer.contains(keyword));
                        break;

                }
            }//end for
            contentsJPQLQuery.where(booleanBuilder);
        }

        contentsJPQLQuery.groupBy(contents);
        contentsJPQLQuery.where(contents.category.contains(genre));
        getQuerydsl().applyPagination(pageable, contentsJPQLQuery);

        JPQLQuery<Tuple> tupleJPQLQuery = contentsJPQLQuery.select(contents, reply.countDistinct());
        List<Tuple> tupleList = tupleJPQLQuery.fetch();

        List<ContentsListAllDTO> dtoList = tupleList.stream().map(tuple -> {
            Contents contents1 = (Contents) tuple.get(contents);
            long replyCount = tuple.get(1, Long.class);

            ContentsListAllDTO dto = ContentsListAllDTO.builder()
                    .id(contents1.getId())
                    .title(contents1.getTitle())
                    .writer(contents1.getWriter())
                    .category(contents1.getCategory())
                    .cType(contents1.getCType())
                    .regDate(contents1.getRegDate())
                    .replyCount(replyCount)
                    .build();

            List<ContentsImageDTO> imageDTOs = contents1.getImageSet().stream().sorted()
                    .map(contentsImage -> ContentsImageDTO.builder()
                            .uuid(contentsImage.getUuid())
                            .fileName(contentsImage.getFileName())
                            .ord(contentsImage.getOrd())
                            .build()
                    ).collect(Collectors.toList());
            dto.setContentsImages(imageDTOs);
            return dto;
        }).collect(Collectors.toList());
        long totalCount = contentsJPQLQuery.fetchCount();
        return new PageImpl<>(dtoList, pageable, totalCount);

    }
    @Override
    public Page<ContentsListAllDTO> searchMovieAll(String[] types, String keyword, Pageable pageable) {

        QContents contents = QContents.contents;
        QReply reply = QReply.reply;

        JPQLQuery<Contents> contentsJPQLQuery = from(contents);
        contentsJPQLQuery.leftJoin(reply).on(reply.contents.eq(contents));

        if((types !=null && types.length>0) && keyword !=null){
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for(String type:types){
                switch(type){
                    case "t":
                        booleanBuilder.or(contents.title.contains(keyword));
                        break;
                    case "e":
                        booleanBuilder.or(contents.explanation.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(contents.writer.contains(keyword));
                        break;

                }
            }//end for
            contentsJPQLQuery.where(booleanBuilder);
        }

        contentsJPQLQuery.groupBy(contents);
        contentsJPQLQuery.where(contents.cType.contains("movie"));
        getQuerydsl().applyPagination(pageable, contentsJPQLQuery);

        JPQLQuery<Tuple> tupleJPQLQuery = contentsJPQLQuery.select(contents, reply.countDistinct());
        List<Tuple> tupleList = tupleJPQLQuery.fetch();

        List<ContentsListAllDTO> dtoList = tupleList.stream().map(tuple -> {
            Contents contents1 = (Contents) tuple.get(contents);
            long replyCount = tuple.get(1, Long.class);

            ContentsListAllDTO dto = ContentsListAllDTO.builder()
                    .id(contents1.getId())
                    .title(contents1.getTitle())
                    .writer(contents1.getWriter())
                    .category(contents1.getCategory())
                    .cType(contents1.getCType())
                    .regDate(contents1.getRegDate())
                    .replyCount(replyCount)
                    .build();

            List<ContentsImageDTO> imageDTOs = contents1.getImageSet().stream().sorted()
                    .map(contentsImage -> ContentsImageDTO.builder()
                            .uuid(contentsImage.getUuid())
                            .fileName(contentsImage.getFileName())
                            .ord(contentsImage.getOrd())
                            .build()
                    ).collect(Collectors.toList());
            dto.setContentsImages(imageDTOs);
            return dto;
        }).collect(Collectors.toList());
        long totalCount = contentsJPQLQuery.fetchCount();
        return new PageImpl<>(dtoList, pageable, totalCount);

    }
    @Override
    public Page<ContentsListAllDTO> searchDramaAll(String[] types, String keyword, Pageable pageable) {

        QContents contents = QContents.contents;
        QReply reply = QReply.reply;

        JPQLQuery<Contents> contentsJPQLQuery = from(contents);
        contentsJPQLQuery.leftJoin(reply).on(reply.contents.eq(contents));

        if((types !=null && types.length>0) && keyword !=null){
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for(String type:types){
                switch(type){
                    case "t":
                        booleanBuilder.or(contents.title.contains(keyword));
                        break;
                    case "e":
                        booleanBuilder.or(contents.explanation.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(contents.writer.contains(keyword));
                        break;

                }
            }//end for
            contentsJPQLQuery.where(booleanBuilder);
        }

        contentsJPQLQuery.groupBy(contents);
        contentsJPQLQuery.where(contents.cType.contains("drama"));
        getQuerydsl().applyPagination(pageable, contentsJPQLQuery);

        JPQLQuery<Tuple> tupleJPQLQuery = contentsJPQLQuery.select(contents, reply.countDistinct());
        List<Tuple> tupleList = tupleJPQLQuery.fetch();

        List<ContentsListAllDTO> dtoList = tupleList.stream().map(tuple -> {
            Contents contents1 = (Contents) tuple.get(contents);
            long replyCount = tuple.get(1, Long.class);

            ContentsListAllDTO dto = ContentsListAllDTO.builder()
                    .id(contents1.getId())
                    .title(contents1.getTitle())
                    .writer(contents1.getWriter())
                    .category(contents1.getCategory())
                    .cType(contents1.getCType())
                    .regDate(contents1.getRegDate())
                    .replyCount(replyCount)
                    .build();

            List<ContentsImageDTO> imageDTOs = contents1.getImageSet().stream().sorted()
                    .map(contentsImage -> ContentsImageDTO.builder()
                            .uuid(contentsImage.getUuid())
                            .fileName(contentsImage.getFileName())
                            .ord(contentsImage.getOrd())
                            .build()
                    ).collect(Collectors.toList());
            dto.setContentsImages(imageDTOs);
            return dto;
        }).collect(Collectors.toList());
        long totalCount = contentsJPQLQuery.fetchCount();
        return new PageImpl<>(dtoList, pageable, totalCount);

    }
    @Override
    public Page<ContentsOttDTO> searchWithOtt(String[] types, String keyword, Pageable pageable) {

        QContents contents = QContents.contents;
        QReply reply = QReply.reply;

        JPQLQuery<Contents> contentsJPQLQuery = from(contents);
        contentsJPQLQuery.leftJoin(reply).on(reply.contents.eq(contents));

        if((types !=null && types.length>0) && keyword !=null){
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for(String type:types){
                switch(type){
                    case "t":
                        booleanBuilder.or(contents.title.eq(keyword));
                        break;
                }
            }//end for
            contentsJPQLQuery.where(booleanBuilder);
        }

        contentsJPQLQuery.groupBy(contents);
        getQuerydsl().applyPagination(pageable, contentsJPQLQuery);

        JPQLQuery<Tuple> tupleJPQLQuery = contentsJPQLQuery.select(contents, reply.countDistinct());
        List<Tuple> tupleList = tupleJPQLQuery.fetch();

        List<ContentsOttDTO> dtoList = tupleList.stream().map(tuple -> {
            Contents contents1 = (Contents) tuple.get(contents);
            long replyCount = tuple.get(1, Long.class);

            ContentsOttDTO dto = ContentsOttDTO.builder()
                    .id(contents1.getId())
                    .title(contents1.getTitle())
                    .netflix(contents1.getNetflix())
                    .disney(contents1.getDisney())
                    .watcha(contents1.getWatcha())
                    .build();

            return dto;
        }).collect(Collectors.toList());
        long totalCount = contentsJPQLQuery.fetchCount();
        return new PageImpl<>(dtoList, pageable, totalCount);

    }
}
