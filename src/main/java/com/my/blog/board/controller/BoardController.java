package com.my.blog.board.controller;

import com.my.blog.board.domain.Board;
import com.my.blog.board.service.BoardService;
import com.my.blog.board.vo.request.BoardRequest;
import com.my.blog.board.vo.response.BoardResponse;
import com.my.blog.category.entity.Category;
import com.my.blog.category.service.CategoryService;
import com.my.blog.common.response.ResponseEnvelope;
import com.my.blog.common.response.ResponseHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;
    private final CategoryService categoryService;

    @PostMapping("/board")
    public ResponseEnvelope<BoardResponse> save(@RequestBody @Valid BoardRequest request){
        Category category = categoryService.findById(request.getCategoryId());
        Board board = request.toEntity();

        Long id = boardService.save(board);

        BoardResponse body =  BoardResponse.builder()
                .id(id)
                .build();

        return new ResponseEnvelope(ResponseHeader.ok(),body);
    }




}
