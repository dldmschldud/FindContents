package com.shop.repository;

import com.shop.domain.Contents;
import com.shop.domain.Reply;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {
    @Autowired
     private ReplyRepository replyRepository;

    @Test
    public void testInsert() {
        Long id = 188L;
        Contents contents = Contents.builder().id(id).build();
        Reply reply = Reply.builder()
                .contents(contents)
                .replyText("댓글.ee....")
                .replyer("user2")
                .build();
        replyRepository.save(reply);


    }


    @Test
    public void testContentsReplies() {
        Long id = 317L;
        Pageable pageable = PageRequest.of(0,10, Sort.by("rno").descending());

        Page<Reply> result = replyRepository.listOfContents(id,pageable);
        result.getContent().forEach(reply -> {log.info(reply);});
    }
}
