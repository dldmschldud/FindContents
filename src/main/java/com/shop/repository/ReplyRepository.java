package com.shop.repository;

import com.shop.domain.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Query("select r from Reply r where r.contents.id = :id")
    Page<Reply> listOfContents(Long id, Pageable pageable);

    void deleteByContents_Id(Long id);
}
