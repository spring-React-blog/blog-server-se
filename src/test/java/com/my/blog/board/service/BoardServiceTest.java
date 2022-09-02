package com.my.blog.board.service;

import com.my.blog.board.domain.Board;
import com.my.blog.board.domain.vo.Content;
import com.my.blog.board.domain.vo.Status;
import com.my.blog.board.domain.vo.Title;
import com.my.blog.board.dto.BoardSchCondition;
import com.my.blog.board.dto.response.BoardResponse;
import com.my.blog.board.repository.BoardRepository;
import com.my.blog.count.entity.BoardCount;
import com.my.blog.count.entity.BoardCountRepository;
import com.my.blog.count.service.BoardCountService;
import com.my.blog.member.entity.vo.Email;
import com.my.blog.support.service.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@UnitTest
class BoardServiceTest {

    private BoardService boardService;
    private BoardSearchService boardSearchService;
  //  BoardCacheService boardCacheService;
    private BoardCountService boardCountService;

    @Mock
    BoardRepository boardRepository;

    @Mock
    BoardCountRepository boardCountRepository;

    @BeforeEach
    public void setUp(){
        //this.boardCacheService = new BoardCacheService();
        this.boardCountService = new BoardCountService(boardCountRepository);
        this.boardService = new BoardService(boardRepository, boardCountService);
        this.boardSearchService = new BoardSearchService(boardRepository);
    }

   /* @AfterEach
    public void teardown() {
        boardRepository.deleteAll();
        this.entityManager
                .createNativeQuery("ALTER TABLE board AUTO_INCREMENT = 1")
                .executeUpdate();
    }
*/
    @Test
    @DisplayName("상세 보드")
    public void getBoard(){
        //given
        BoardCount countGiven = BoardCount.builder()
                .id(1L)
                .viewCount(0L)
                .build();
        Board given = Board.builder()
                .id(1L)
                .content(Content.from("ff"))
                .title(Title.from("dd"))
                .status(Status.TRUE)
                .boardCount(countGiven)
                .build();

        given(boardCountRepository.findById(1L)).willReturn(Optional.of(countGiven));
        given(boardRepository.findById(1L)).willReturn(Optional.of(given));

        //when
        Board board = boardService.getBoard(1L);

        //then
        Long viewCount = board.getBoardCount().getViewCount();
        assertThat(board.getId()).isEqualTo(1L);
        assertThat(viewCount).isEqualTo(1);

    }

    @Test
    @DisplayName("전체 보드 리스트")
    public void getBoardList(){
        //given
        BoardSchCondition condition =  BoardSchCondition.builder()
                .title(Title.from("welcome"))
                .build()
                ;
        PageRequest pageable = PageRequest.of(0, 10);

        BoardResponse boardResponse = BoardResponse.builder().id(1L)
                .title(Title.from("welcome"))
                .content(Content.from("hello world"))
                .categoryName("spring")
                .email(Email.from("seung90@gmail.com"))
                .viewCount(0)
                .build();
        Page<BoardResponse> responses = new PageImpl<>(List.of(boardResponse));
        given(boardRepository.search(condition,pageable)).willReturn(responses);

        //when
        Page<BoardResponse> boards = boardSearchService.getBoards(condition, pageable);


        //then
        List<BoardResponse> content = boards.getContent();
        int totalPages = boards.getTotalPages();

        assertThat(content.size()).isEqualTo(1);
        assertThat(totalPages).isEqualTo(1);
    }


}