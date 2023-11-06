package com.shop.service;

import com.shop.dto.ReplyDTO;
import com.shop.repository.ReplyRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ReplyServiceTest {

    @Autowired
    ReplyService replyService;
    @Test
    public void testRegister() {
        ReplyDTO replyDTO = ReplyDTO.builder()
                .replyText("CHECK")
                .replyer("CHECKK")
                .id(312L)
                .build();

        log.info(replyService.register(replyDTO));
    }
}
