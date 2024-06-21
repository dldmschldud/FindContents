package com.shop.repository;

import com.shop.domain.Contents;
import com.shop.domain.Reply;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.hibernate.validator.constraints.Length;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ContentsRepository contentsRepository;

    /*
    @Test
    public void insertReplies(){
        Optional<Contents> result = contentsRepository.findById(100L);
        Contents contents = result.orElseThrow();

        IntStream.rangeClosed(5006,9000).forEach(i -> {
            Reply reply = Reply.builder()
                    .rid((long) i)
                    .replyer("user1")
                    .replyText("fun!")
                    .contents(contents)
                    .build();
            replyRepository.save(reply);

        });
    }
    @Test
    public void findReplies(){
        Optional<Reply> result = replyRepository.findById(5004L);
        Reply reply = result.orElseThrow();
        log.info(reply);
    }*/


}
