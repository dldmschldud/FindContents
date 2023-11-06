package com.shop.service;

import com.shop.dto.PageRequestDTO;
import com.shop.dto.PageResponseDTO;
import com.shop.dto.ThemeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ThemeService {

    PageResponseDTO<ThemeDTO> list(PageRequestDTO pageRequestDTO);

    Long register(ThemeDTO themeDTO);
    ThemeDTO readTheme(Long tid);

    void modify(ThemeDTO themeDTO);

    void remove(Long tid);
}
