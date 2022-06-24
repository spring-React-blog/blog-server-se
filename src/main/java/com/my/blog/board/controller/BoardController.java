package com.my.blog.board.controller;

import com.my.blog.board.domain.Board;
import com.my.blog.board.service.BoardSearchService;
import com.my.blog.board.service.BoardService;
import com.my.blog.board.dto.BoardSchCondition;
import com.my.blog.board.dto.request.BoardRequest;
import com.my.blog.board.dto.response.BoardResponse;
import com.my.blog.category.entity.Category;
import com.my.blog.category.service.CategoryService;
import com.my.blog.global.common.response.ResponseEnvelope;
import com.my.blog.global.common.response.ResponseHeader;
import com.my.blog.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;
    private final BoardSearchService boardSearchService;

    @GetMapping("/boards")
    public ResponseEntity<BoardResponse> getList(BoardSchCondition condition, Pageable pageable){
        Page<BoardResponse> boards = boardSearchService.getBoards(condition, pageable);

        return new ResponseEntity(boards,HttpStatus.OK);
    }

    @GetMapping("/boards/{boardId}")
    public ResponseEntity<BoardResponse> getOne(@PathVariable Long boardId){
        Board board = boardService.getBoard(boardId);
        BoardResponse boardResponse = BoardResponse.toResponse(board);

        return new ResponseEntity(boardResponse,HttpStatus.OK);
    }

   /* @GetMapping("/boards/category/{parents}/{child}")
    public ResponseEnvelope<BoardResponse> getOne(@PathVariable String parents, @PathVariable String child){


        return new ResponseEntity(HttpStatus.OK);
    }*/

    @PostMapping("/boards")
    public ResponseEntity<Long> save(@RequestBody @Valid BoardRequest request){
        Board board = request.toEntity();
        Long id = boardService.save(board);

        return new ResponseEntity(id,HttpStatus.OK);
    }


    @PutMapping("/boards")
    public ResponseEntity<Long> update(@RequestBody @Valid BoardRequest request, @AuthenticationPrincipal CustomUserDetails user){
        Board board = request.toEntity();
        boardService.updateBoard(board, board.getId(), user.getUsername());

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity<BoardResponse> delete(@PathVariable Long boardId, @AuthenticationPrincipal CustomUserDetails user){
        boardService.deleteBoard(boardId, user.getUsername());

        return new ResponseEntity(HttpStatus.OK);
    }

}
