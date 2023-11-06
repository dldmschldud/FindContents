package com.shop.repository.search;

import com.shop.domain.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ThemeSearch {

    Page<Theme> searchAll(String[] types, String keyword, Pageable pageable);
}
