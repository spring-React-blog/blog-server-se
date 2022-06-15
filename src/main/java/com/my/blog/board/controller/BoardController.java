package com.my.blog.board.controller;

import com.my.blog.board.domain.Board;
import com.my.blog.board.service.BoardService;
import com.my.blog.board.dto.BoardSchCondition;
import com.my.blog.board.dto.request.BoardRequest;
import com.my.blog.board.dto.response.BoardResponse;
import com.my.blog.category.entity.Category;
import com.my.blog.category.service.CategoryService;
import com.my.blog.common.response.ResponseEnvelope;
import com.my.blog.common.response.ResponseHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;
    private final CategoryService categoryService;

    @PostMapping("/boards")
    public ResponseEnvelope<BoardResponse> save(@RequestBody @Valid BoardRequest request){
        Category category = categoryService.findById(request.getCategoryId());
        Board board = request.toEntity();

        Long id = boardService.save(board);

        BoardResponse body =  BoardResponse.builder()
                .id(id)
                .build();

        return new ResponseEnvelope(ResponseHeader.ok(),body);
    }

    @GetMapping("/boards")
    public ResponseEnvelope<BoardResponse> list(@RequestBody BoardSchCondition condition, Pageable pageable){

        Page<BoardResponse> boards = boardService.getBoards(condition, pageable);

        List<BoardResponse> content = boards.getContent();

        return new ResponseEnvelope(ResponseHeader.ok(),content);
    }


}
