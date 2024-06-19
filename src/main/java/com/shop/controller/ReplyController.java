package com.shop.controller;

import com.shop.domain.Reply;
import com.shop.dto.PageRequestDTO;
import com.shop.dto.PageResponseDTO;
import com.shop.dto.ReplyDTO;
import com.shop.service.ReplyService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor
public class  ReplyController {
    private final ReplyService replyService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Long>> register(@Valid @RequestBody ReplyDTO replyDTO,
                                     BindingResult bindingResult)throws BindException {
        if(bindingResult.hasErrors()){
            throw new BindException((bindingResult));
        }
        Map<String,Long> result = new HashMap<>();
        Long rid = replyService.register(replyDTO);
        result.put("rid",rid);
        return ResponseEntity.ok().body(result);
    }


    @GetMapping("/list/{id}")
    public ResponseEntity getList(@PathVariable("id") Long id, PageRequestDTO pageRequestDTO){
        PageResponseDTO<ReplyDTO> responseDTO = replyService.getListOfContents(id,pageRequestDTO);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @GetMapping("/{rid}")
    public ResponseEntity<ReplyDTO> getReplyDTO(@PathVariable("rid") Long rid){
        ReplyDTO reply = replyService.read(rid);
        return ResponseEntity.ok(reply);
    }

    @DeleteMapping("/{rid}")
    public ResponseEntity<Map<String,Long>> remove(@PathVariable("rid") Long rid){
        replyService.remove(rid);
        Map<String,Long> result = new HashMap<>();
        result.put("rid",rid);

        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/{rid}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Long>> modify(@PathVariable("rid") Long rid, @RequestBody
    ReplyDTO replyDTO){
        log.info(replyDTO);
        replyService.modify(replyDTO);
        Map<String,Long> result = new HashMap<>();
        result.put("rid",rid);
        return ResponseEntity.ok().body(result);

    }



    /*
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Long> register(@Valid @RequestBody ReplyDTO replyDTO,
                                     BindingResult bindingResult)throws BindException {
        log.info(replyDTO);
        if(bindingResult.hasErrors()){
            throw new BindException((bindingResult));
        }
        Map<String,Long> resultMap = new HashMap<>();
        Long rid = replyService.register(replyDTO);
        resultMap.put("rid",rid);

        return resultMap;
    }


    @GetMapping(value = "/list/{id}")
    public PageResponseDTO<ReplyDTO> getList(@PathVariable("id") Long id,
                                             PageRequestDTO pageRequestDTO){
        PageResponseDTO<ReplyDTO> responseDTO = replyService.getListOfContents(id,pageRequestDTO);
        log.info(pageRequestDTO);


        return responseDTO;

    }
    @GetMapping(value = "/{rid}")
    public ReplyDTO getReplyDTO(@PathVariable("rid") Long rid){

        ReplyDTO replyDTO = replyService.read(rid);
        log.info(replyDTO);
        return replyDTO;
    }


    @GetMapping("/{rid}")
    public ResponseEntity getReplyDTO(@PathVariable("rid") Long rid){
        ReplyDTO reply = replyService.read(rid);
        return new ResponseEntity<>(reply,HttpStatus.OK);

    }

    @DeleteMapping("/{rid}")
    public Map<String,Long> remove(@PathVariable("rid") Long rid){
        replyService.remove(rid);
        Map<String,Long> resultMap = new HashMap<>();
        resultMap.put("rid",rid);

        return resultMap;
    }


    @PutMapping(value = "/{rid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Long> modify(@PathVariable("rid") Long rid, @RequestBody ReplyDTO replyDTO) {
        log.info(replyDTO);
        replyDTO.setRid(rid);
        replyService.modify(replyDTO);
        Map<String,Long>resultMap=new HashMap<>();
        resultMap.put("rid",rid);

        return resultMap;
    }

     */
}
