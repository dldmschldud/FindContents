package com.shop.repository;

import com.shop.domain.Contents;
import com.shop.domain.ContentsImage;
import com.shop.dto.ContentsDTO;
import com.shop.dto.ContentsListAllDTO;
import com.shop.dto.ContentsListReplyCountDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.LongStream;


@SpringBootTest
@Log4j2
public class ContentsRepositoryTests {

    @Autowired
    private ContentsRepository contentsRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void testInsert() {

        IntStream.rangeClosed(1,2).forEach(i -> {
            Contents contents = Contents.builder()
                    .title("title..." +i)
                    .explanation("explanation..." +i)
                    .writer("admin" + (i%10))
                    .watcha("Y")
                    .disney("Y")
                    .netflix("Y")
                    .category("romance")
                    .cType("movie")
                    .build();
            Contents result = contentsRepository.save(contents);
        });

    }

    @Test
    public void testInsertAll() {
        for (int i=1;i<=10;i++){
            Contents contents = Contents.builder()
                    .title("this is title "+i)
                    .explanation("this is explanation"+i)
                    .writer("Writer"+i)
                    .cType("movie")
                    .category("romance")
                    .build();

            contentsRepository.save(contents);
        }
    }
    @Test
    public void testSelect() {
        Long id = 99L;
        Optional<Contents> result = contentsRepository.findById(id);
        Contents contents = result.orElseThrow();
        log.info(contents);
    }



    @Test
    public void testDelete() {
        LongStream.rangeClosed(150,200).forEach(i -> {
            contentsRepository.deleteById(i);

        });


    }


    @Test
    public void testSearch1() {
        Pageable pageable = PageRequest.of(1,10, Sort.by("id").descending());
        contentsRepository.search1(pageable);
    }

    @Test
    public void testSearchAll() {
        String[] types = {"t","e","w"};
        String keyword = "1";
        Pageable pageable = PageRequest.of(0,10,Sort.by("id").descending());
        Page<Contents> result = contentsRepository.searchAll(types, keyword,pageable);

        //total pages
        log.info(result.getTotalPages());

        //pag size
        log.info(result.getSize());

        //pageNumber
        log.info(result.getNumber());

        //prev next
        log.info(result.hasPrevious() + ": " + result.hasNext());

        result.getContent().forEach(contents -> log.info(contents));
    }


    @Test
    public void testSearchReplyCount() {
        String[] types = {"t","e","w"};
        String keyword = "9";
        Pageable pageable = PageRequest.of(0,10,Sort.by("id").descending());
        Page<ContentsListReplyCountDTO> result = contentsRepository.searchWithReplyCount(types, keyword,pageable);

        //total pages
        log.info(result.getTotalPages());

        //pag size
        log.info(result.getSize());

        //pageNumber
        log.info(result.getNumber());

        //prev next
        log.info(result.hasPrevious() + ": " + result.hasNext());

        result.getContent().forEach(contents -> log.info(contents));
    }

    @Test
    public void testInsertWithImages() {
        Contents contents = Contents.builder()
                .title("Image test")
                .explanation("첨부파일 테스트")
                .writer("나야나")
                .build();

        for (int i=0;i<3;i++) {
            contents.addImage(UUID.randomUUID().toString(), "file"+i+".jpg");
        }
        contentsRepository.save(contents);
    }
    @Test
    public void testReadWithImages() {
        //Optional<Contents> result = contentsRepository.findById(116L);
        Optional<Contents> result = contentsRepository.findByIdWithImages(336L);
        Contents contents = result.orElseThrow();
        log.info(contents);
        log.info("________________");
        for(ContentsImage contentsImage : contents.getImageSet()) {
            log.info(contentsImage);
        }
        //log.info(contents.getImageSet());
    }

    @Test
    @Transactional
    @Commit
    public void testModifyImages() {
        Optional<Contents> result = contentsRepository.findByIdWithImages(336L);
        Contents contents = result.orElseThrow();

        contents.clearImages();
        for(int i=0;i<2;i++){
            contents.addImage(UUID.randomUUID().toString(),"newupdate"+i+".jpg");
        }

        contentsRepository.save(contents);
    }

    @Test
    @Transactional
    @Commit
    public void testRemoveAll() {
        for (Long i=157L;i<188;i++){
            replyRepository.deleteByContents_Id(i);
            contentsRepository.deleteById(i);
        }

    }
    @Test
    @Transactional
    @Commit
    public void testRemove() {
        for (Long i=173L;i<177L;i++){
            contentsRepository.deleteById(i);
        }

    }

    @Test
    @Transactional
    public void testSearchImageReplyCount() {
        Pageable pageable = PageRequest.of(0,10,Sort.by("id").descending());
       // contentsRepository.searchWithAll(null,null,pageable);
        Page<ContentsListAllDTO> result = contentsRepository.searchWithAll(null,null,pageable);

        log.info("+++++++++++++++++++++++++++++++++++++++");
        log.info(result.getTotalElements());
        result.getContent().forEach(contentsListAllDTO -> log.info(contentsListAllDTO));
    }



}
