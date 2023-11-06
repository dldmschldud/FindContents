package com.shop.service;

import com.shop.dto.PageRequestDTO;
import com.shop.dto.PageResponseDTO;
import com.shop.dto.ReplyDTO;

public interface ReplyService {
    Long register(ReplyDTO replyDTO);

    ReplyDTO read(Long rid);

    void modify(ReplyDTO replyDTO);

    void remove(Long rid);

    PageResponseDTO<ReplyDTO> getListOfContents(Long id, PageRequestDTO pageRequestDTO);
}
