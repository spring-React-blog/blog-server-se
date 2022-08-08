package com.my.blog.board.controller;

import com.my.blog.board.domain.Board;
import com.my.blog.board.domain.BoardImage;
import com.my.blog.board.dto.BoardSchCondition;
import com.my.blog.board.dto.request.CreateRequest;
import com.my.blog.board.dto.request.UpdateRequest;
import com.my.blog.board.dto.response.BoardResponse;
import com.my.blog.board.service.BoardSearchService;
import com.my.blog.board.service.BoardService;
import com.my.blog.category.entity.Category;
import com.my.blog.category.service.CategoryService;
import com.my.blog.file.service.UploadService;
import com.my.blog.member.service.MemberService;
import com.my.blog.member.service.dto.EntityMapper;
import com.my.blog.member.service.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;
    private final CategoryService categoryService;
    private final MemberService memberService;
    private final BoardSearchService boardSearchService;
    private final UploadService uploadService;
    private final EntityMapper entityMapper;

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

    @PostMapping("/boards")
    public ResponseEntity<BoardResponse> save(@Valid @RequestBody CreateRequest request, @AuthenticationPrincipal String user ){

        MemberDTO memberDTO = memberService.findByEmail(user);
        Category category = categoryService.findById(request.getCategoryId());
        List<BoardImage> boardImages = request.toBoardImageEntityList();
        Board board = request.toBoardEntity(boardImages,entityMapper.toEntity(memberDTO),category);
        Board saved = boardService.save(board);
        BoardResponse boardResponse = BoardResponse.toResponse(saved);

        return new ResponseEntity(boardResponse, HttpStatus.OK);
    }

    @PutMapping("/boards")
    public ResponseEntity<BoardResponse> update(@Valid @RequestBody UpdateRequest request, @AuthenticationPrincipal String user ){
        Category category = categoryService.findById(request.getCategoryId());

        Board board = request.toBoardEntity(category);
        Board updateBoard = boardService.updateBoard(board, user);
        BoardResponse boardResponse = BoardResponse.toResponse(updateBoard);

        return new ResponseEntity(boardResponse, HttpStatus.OK);
    }

    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity<BoardResponse> delete(@PathVariable Long boardId, @AuthenticationPrincipal String user){
        boardService.deleteBoard(boardId, user);

        return new ResponseEntity(HttpStatus.OK);
    }

}
