package com.shop.repository;

import com.shop.domain.Contents;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ContentsRepositoryTests {

    @Autowired
    private ContentsRepository contentsRepository;

    @Autowired
    private ReplyRepository replyRepository;

}
