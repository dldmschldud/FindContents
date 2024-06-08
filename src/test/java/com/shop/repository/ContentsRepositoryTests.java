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
