package com.my.blog.board.api;

import com.my.blog.board.domain.Board;
import com.my.blog.board.service.BoardService;
import com.my.blog.board.vo.request.BoardRequest;
import com.my.blog.board.vo.response.BoardResponse;
import com.my.blog.common.response.ResponseEnvelope;
import com.my.blog.common.response.ResponseHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class BoardAPIController {

    private final BoardService boardService;

    @PostMapping("/board")
    public ResponseEnvelope<BoardResponse> save(@RequestBody @Valid BoardRequest request){
        Board board = request.toEntity();
        Long id = boardService.save(board);

        ResponseHeader header = ResponseHeader.builder()
                .code("200")
                .message("success")
                .build();

        BoardResponse body =  BoardResponse.builder()
                .id(id)
                .build();

        return new ResponseEnvelope(header,body);
    }




}
