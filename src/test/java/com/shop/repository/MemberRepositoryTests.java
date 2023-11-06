package com.shop.repository;

import com.shop.domain.Member;
import com.shop.domain.MemberRole;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insert(){
        Member member = Member.builder()
                .mid("admin3")
                .mpw(passwordEncoder.encode("admin1234"))
                .email("admin3@email.com")
                .build();

        member.addRole(MemberRole.USER);
        member.addRole(MemberRole.ADMIN);

        memberRepository.save(member);
        log.info(member);
    }
    @Test
    public void insertMember(){


        IntStream.rangeClosed(1,3).forEach(i -> {
            Member member = Member.builder()
                    .mid("admin"+i)
                    .mpw(passwordEncoder.encode("admin1234"))
                    .email("email"+i+"@"+"movie.com")
                    .build();

            member.addRole(MemberRole.USER);
            if(i>=1){
                member.addRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);
        });
    }
    @Test
    public void insertMembers(){
        IntStream.range(12,13).forEach(i -> {
            Member member = Member.builder()
                    .mid("member"+i)
                    .mpw(passwordEncoder.encode("1111"))
                    .email("email"+i+"@"+"movie.com")
                    .build();

            member.addRole(MemberRole.USER);
            if(i>=9){
                member.addRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);
        });
    }

    @Test
    public void testRead(){
        Optional<Member> member1 = memberRepository.findById("member20");

        log.info(member1);


    }
}
