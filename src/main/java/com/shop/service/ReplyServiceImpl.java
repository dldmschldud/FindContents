package com.shop.service;

import com.shop.domain.Reply;
import com.shop.dto.PageRequestDTO;
import com.shop.dto.PageResponseDTO;
import com.shop.dto.ReplyDTO;
import com.shop.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long register(ReplyDTO replyDTO){
        Reply reply = modelMapper.map(replyDTO,Reply.class);

        Long rid = replyRepository.save(reply).getRid();

        return rid;
    }

    @Override
    public ReplyDTO read(Long rid){
        Optional<Reply> replyOptional = replyRepository.findById(rid);
        Reply reply = replyOptional.orElseThrow();
        return modelMapper.map(reply,ReplyDTO.class);
    }

    @Override
    @Transactional
    public void modify(ReplyDTO replyDTO) {
        Optional<Reply> replyOptional = replyRepository.findById(replyDTO.getRid());
        Reply reply = replyOptional.orElseThrow();
        reply.changeText(replyDTO.getReplyText());
    }

    @Override
    public void remove(Long rid) {
        replyRepository.deleteById(rid);
    }

    @Override
    public PageResponseDTO<ReplyDTO> getListOfContents(Long id, PageRequestDTO pageRequestDTO){
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <=0? 0: pageRequestDTO.getPage() -1,
                pageRequestDTO.getSize(), Sort.by("rid").ascending());
        Page<Reply> result = replyRepository.listOfContents(id,pageable);

        List<ReplyDTO> dtoList = result.getContent().stream().map(reply -> modelMapper.map(reply, ReplyDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<ReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();

    }


}
