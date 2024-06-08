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

    /*
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
     */
    @Test
    public void insertUserMembers(){
        IntStream.rangeClosed(1,2).forEach(i -> {
            Member member = Member.builder()
                    .mid("user"+i)
                    .mpw(passwordEncoder.encode("user12341234"))
                    .email("user" +i+ "@"+"google.com")
                    .build();
            member.addRole(MemberRole.USER);
            memberRepository.save(member);
        });
    }
    @Test
    public void insertAdminMembers(){
        IntStream.range(100,102).forEach(i -> {
            Member member = Member.builder()
                    .mid("admin"+i)
                    .mpw(passwordEncoder.encode("admin12341234"))
                    .email("admin" +i+ "@"+"google.com")
                    .build();

            member.addRole(MemberRole.USER);
            if(i>=100 && i<=102){
                member.addRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);
        });
    }
    @Test
    public void insertAdminMember(){
        Member member = Member.builder()
                .mid("adminYSH")
                .mpw(passwordEncoder.encode("admin12341234"))
                .email("adminYSH@google.com")
                .build();
        member.addRole(MemberRole.USER);
        member.addRole(MemberRole.ADMIN);
        memberRepository.save(member);
    }

    @Test
    public void testRead(){
        Optional<Member> memberTest = memberRepository.findById("adminYSH");
        log.info(memberTest);
    }
}
