package com.shop.repository.search;

import com.shop.domain.Contents;
import com.shop.dto.ContentsListAllDTO;
import com.shop.dto.ContentsListReplyCountDTO;
import com.shop.dto.ContentsOttDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContentsSearch {
    Page<Contents> search1(Pageable pageable);
    Page<Contents> searchAll(String[] types, String keyword, Pageable pageable);

    Page<ContentsListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable);

    //Page<ContentsListReplyCountDTO> searchWithAll(String[] types, String keyword,Pageable pageable);
    Page<ContentsListAllDTO> searchWithAll(String[] types, String keyword, Pageable pageable);

    Page<ContentsListAllDTO> searchRomanceAll(String genre, String[] types, String keyword, Pageable pageable);
    Page<ContentsListAllDTO> searchMovieAll(String[] types, String keyword, Pageable pageable);

    Page<ContentsListAllDTO> searchDramaAll(String[] types, String keyword, Pageable pageable);

    Page<ContentsOttDTO> searchWithOtt(String[] types, String keyword, Pageable pageable);

}
