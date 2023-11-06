package com.shop.repository;

import com.shop.domain.Theme;
import com.shop.repository.search.ThemeSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme, Long>,ThemeSearch{
}
