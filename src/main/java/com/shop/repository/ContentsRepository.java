package com.shop.repository;

import com.shop.domain.Contents;
import com.shop.repository.search.ContentsSearch;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ContentsRepository extends JpaRepository<Contents, Long>, ContentsSearch {//엔티티타입,id타입

    @Query(value = "select now()", nativeQuery = true)
    String getTime();




}
