package com.my.blog.board.controller;

import com.my.blog.board.domain.Board;
import com.my.blog.board.domain.BoardImage;
import com.my.blog.board.dto.BoardSchCondition;
import com.my.blog.board.dto.request.BoardRequest;
import com.my.blog.board.dto.request.ImageDTO;
import com.my.blog.board.dto.response.BoardResponse;
import com.my.blog.board.service.BoardSearchService;
import com.my.blog.board.service.BoardService;
import com.my.blog.category.entity.Category;
import com.my.blog.category.service.CategoryService;
import com.my.blog.global.common.utils.FileUtil;
import com.my.blog.global.security.CustomUserDetails;
import com.my.blog.member.service.MemberService;
import com.my.blog.member.service.dto.EntityMapper;
import com.my.blog.member.service.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;
    private final CategoryService categoryService;
    private final MemberService memberService;
    private final BoardSearchService boardSearchService;
    private final FileUtil fileUtil;
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

   /* @GetMapping("/boards/category/{parents}/{child}")
    public ResponseEnvelope<BoardResponse> getOne(@PathVariable String parents, @PathVariable String child){


        return new ResponseEntity(HttpStatus.OK);
    }*/

    @PostMapping(value="/boards")
    public ResponseEntity<BoardResponse> save( @Valid BoardRequest request, @AuthenticationPrincipal String user ){
        List<BoardImage> boardImages = new ArrayList<>();
        if(request.getFiles() != null){
            List<ImageDTO> imageList = fileUtil.getNamesAfterUpload(request.getFiles());
            boardImages = request.toBoardImageEntityList(imageList);
        }

        MemberDTO memberDTO = memberService.findByEmail(user);
        Category category = categoryService.findById(request.getCategoryId());


        Board board = request.toBoardEntity(boardImages, entityMapper.toEntity(memberDTO),category);
        Board saved = boardService.save(board);

        System.out.println(board.getMember());
        System.out.println(request.getCategoryId() + ", "+board.getCategory());
        BoardResponse boardResponse = BoardResponse.toResponse(saved);

        return new ResponseEntity(boardResponse, HttpStatus.OK);
    }

    @PutMapping("/boards")
    public ResponseEntity<Long> update( @Valid BoardRequest request, @AuthenticationPrincipal String user ){
        List<ImageDTO> imageList = fileUtil.getNamesAfterUpload(request.getFiles());
        List<BoardImage> boardImages = request.toBoardImageEntityList(imageList);
        MemberDTO memberDTO = memberService.findByEmail(user);
        Category category = categoryService.findById(request.getCategoryId());

        Board board = request.toBoardEntity(boardImages, entityMapper.toEntity(memberDTO),category);

        boardService.updateBoard(board, board.getId(), user);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity<BoardResponse> delete(@PathVariable Long boardId, @AuthenticationPrincipal String user){
        boardService.deleteBoard(boardId, user);

        return new ResponseEntity(HttpStatus.OK);
    }

}
