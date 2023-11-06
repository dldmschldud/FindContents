package com.shop.service;

import com.shop.domain.Contents;
import com.shop.dto.*;
import com.shop.repository.ContentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
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
public class ContentsServiceImpl implements ContentsService{

    private final ModelMapper modelMapper;
    private final ContentsRepository contentsRepository;

    @Override
    public Long register(ContentsDTO contentsDTO) {
        //Contents contents = modelMapper.map(contentsDTO, Contents.class);
        Contents contents = dtoToEntity(contentsDTO);

        Long id = contentsRepository.save(contents).getId();

        return id;

    }

    @Override
    public ContentsDTO readOne(Long id) {
        //Optional<Contents> result = contentsRepository.findById(id);
        Optional<Contents> result = contentsRepository.findByIdWithImages(id);
        Contents contents = result.orElseThrow();
        //ContentsDTO contentsDTO = modelMapper.map(contents, ContentsDTO.class);
        ContentsDTO contentsDTO = entityToDTO(contents);
        log.info(contents);
        return contentsDTO;

    }

    @Override
    public void modify(ContentsDTO contentsDTO) {
        Optional<Contents> result = contentsRepository.findById(contentsDTO.getId());
        Contents contents = result.orElseThrow();
        contents.change(contentsDTO.getTitle(),contentsDTO.getExplanation(),contentsDTO.getWriter(),contentsDTO.getCategory(),contentsDTO.getCType(),contentsDTO.getNetflix(),contentsDTO.getDisney(),contentsDTO.getWatcha()
        );
        contents.clearImages();

        if(contentsDTO.getFileNames() != null) {
            for(String fileName: contentsDTO.getFileNames()) {
                String [] arr = fileName.split("_");
                contents.addImage(arr[0],arr[1]);
            }
            contentsRepository.save(contents);
        }



    }

    @Override
    public void remove(Long id) {
        contentsRepository.deleteById(id);
    }

    @Override
    public PageResponseDTO<ContentsDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("id");

        Page<Contents> result = contentsRepository.searchAll(types, keyword, pageable);

        List<ContentsDTO> dtoList = result.getContent().stream()
                .map(contents -> modelMapper.map(contents,ContentsDTO.class)).collect(Collectors.toList());

        return PageResponseDTO.<ContentsDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
    @Override
    public PageResponseDTO<ContentsListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("id");

        Page<ContentsListReplyCountDTO> result = contentsRepository.searchWithReplyCount(types, keyword, pageable);

        return PageResponseDTO.<ContentsListReplyCountDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();

    }


    @Override
    public PageResponseDTO<ContentsListAllDTO> listWithAll(PageRequestDTO pageRequestDTO){
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("id");

        Page<ContentsListAllDTO> result = contentsRepository.searchWithAll(types,keyword,pageable);


        return PageResponseDTO.<ContentsListAllDTO>withAll()
                .pageRequestDTO(pageRequestDTO)//default page=1, size=10
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<ContentsListAllDTO> genreList(String genre,PageRequestDTO pageRequestDTO){
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("id");

        Page<ContentsListAllDTO> result = contentsRepository.searchRomanceAll(genre,types,keyword,pageable);



        return PageResponseDTO.<ContentsListAllDTO>withAll()
                .pageRequestDTO(pageRequestDTO)//default page=1, size=10
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<ContentsListAllDTO> movieList(PageRequestDTO pageRequestDTO){
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("id");

        Page<ContentsListAllDTO> result = contentsRepository.searchMovieAll(types,keyword,pageable);


        return PageResponseDTO.<ContentsListAllDTO>withAll()
                .pageRequestDTO(pageRequestDTO)//default page=1, size=10
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    }
    @Override
    public PageResponseDTO<ContentsListAllDTO> dramaList(PageRequestDTO pageRequestDTO){
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("id");

        Page<ContentsListAllDTO> result = contentsRepository.searchDramaAll(types,keyword,pageable);


        return PageResponseDTO.<ContentsListAllDTO>withAll()
                .pageRequestDTO(pageRequestDTO)//default page=1, size=10
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    }

    public PageResponseDTO<ContentsOttDTO> listWithOtt(PageRequestDTO pageRequestDTO){
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("id");

        Page<ContentsOttDTO> result = contentsRepository.searchWithOtt(types,keyword,pageable);

        return PageResponseDTO.<ContentsOttDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    }
}
