package com.thc.sprbasic2025.controller;

import com.thc.sprbasic2025.domain.Board;
import com.thc.sprbasic2025.dto.BoardDto;
import com.thc.sprbasic2025.dto.DefaultDto;
import com.thc.sprbasic2025.security.PrincipalDetails;
import com.thc.sprbasic2025.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/board")
@RestController
public class BoardRestController {

    final BoardService boardService;
    public BoardRestController(BoardService boardService){
        this.boardService = boardService;
    }

    @PostMapping("")
    public ResponseEntity<DefaultDto.CreateResDto> create(@RequestBody BoardDto.CreateReqDto params, HttpServletRequest request){
        Long reqUserId = (Long) request.getAttribute("reqUserId");
        params.setReqUserId(reqUserId);
        return ResponseEntity.ok(boardService.create(params));
    }

    @PutMapping("")
    public ResponseEntity<Void> update(@RequestBody BoardDto.UpdateReqDto params, HttpServletRequest request){
        Long reqUserId = (Long) request.getAttribute("reqUserId");
        params.setReqUserId(reqUserId);
        boardService.update(params);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping("")
    public ResponseEntity<Void> delete(@RequestBody DefaultDto.DeleteReqDto params){
        boardService.delete(params);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/detail")
    public ResponseEntity<BoardDto.DetailResDto> detail(DefaultDto.DetailReqDto params){
        return ResponseEntity.ok(boardService.detail(params));
    }


    @PreAuthorize("permitAll()")
    //@PreAuthorize("hasRole('USER')")
    @GetMapping("/list")
    public ResponseEntity<List<BoardDto.DetailResDto>> list(BoardDto.ListReqDto params
            , HttpServletRequest request, @AuthenticationPrincipal PrincipalDetails principalDetails
    ){
        //Long reqUserId = (Long) request.getAttribute("reqUserId");
        Long reqUserId = null;
        if(principalDetails != null){
            reqUserId = principalDetails.getUser().getId();
        }
        System.out.println("reqUserId : " + reqUserId);

        return ResponseEntity.ok(boardService.list(params));
    }

    @GetMapping("/pagedList")
    public ResponseEntity<DefaultDto.PagedListResDto> pagedList(BoardDto.PagedListReqDto params){
        return ResponseEntity.ok(boardService.pagedList(params));
    }
    @GetMapping("/scrollList")
    public ResponseEntity<List<BoardDto.DetailResDto>> scrollList(BoardDto.ScrollListReqDto params){
        return ResponseEntity.ok(boardService.scrollList(params));
    }

}
