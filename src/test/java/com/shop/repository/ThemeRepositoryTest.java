package com.shop.repository;

import com.shop.domain.Theme;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class ThemeRepositoryTest {

    @Autowired
    ThemeRepository themeRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(11,20).forEach(i -> {
            Theme theme = Theme.builder()
                    .thTitle("title"+i)
                    .thExplanation("explanation"+i)
                    .thWriter("user"+i)
                    .build();
            Theme result = themeRepository.save(theme);

        });
    }
    @Test
    public void testDelete() {
        for (Long i=1L;i<18L;i++){
            themeRepository.deleteById(i);
        }
    }




}