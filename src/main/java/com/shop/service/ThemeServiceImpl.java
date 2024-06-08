package com.shop.service;

import com.shop.domain.Theme;
import com.shop.dto.PageRequestDTO;
import com.shop.dto.PageResponseDTO;
import com.shop.dto.ThemeDTO;
import com.shop.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ThemeServiceImpl implements ThemeService{

    private final ModelMapper modelMapper;
    private final ThemeRepository themeRepository;

    @Override
    public PageResponseDTO<ThemeDTO> list(PageRequestDTO pageRequestDTO){
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("tid");
        Page<Theme> result = themeRepository.searchAll(types,keyword,pageable);

        List<ThemeDTO> dtoList = result.getContent().stream()
                .map(theme -> modelMapper.map(theme,ThemeDTO.class)).collect(Collectors.toList());

        return PageResponseDTO.<ThemeDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public Long register(ThemeDTO themeDTO){
        Theme theme = modelMapper.map(themeDTO, Theme.class);
        Long tid = themeRepository.save(theme).getTid();
        return tid;
    }
    @Override
    public ThemeDTO readTheme(Long tid){
        Optional<Theme> result = themeRepository.findById(tid);
        Theme theme = result.orElseThrow();
        ThemeDTO themeDTO = ThemeDTO.builder()
                .tid(theme.getTid())
                .thTitle(theme.getThTitle())
                .thExplanation(theme.getThExplanation())
                .thWriter(theme.getThWriter())
                .build();

        return themeDTO;

    }
    @Override
    public void modify(ThemeDTO themeDTO){
        Optional<Theme> result = themeRepository.findById(themeDTO.getTid());
        Theme theme = result.orElseThrow();
        theme.change(themeDTO.getThTitle(),themeDTO.getThExplanation());

    }

    @Override
    public void remove(Long tid){
        themeRepository.deleteById(tid);

    }

}
