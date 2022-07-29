package com.my.blog.board.service;

import com.my.blog.board.domain.Board;
import com.my.blog.board.domain.vo.Content;
import com.my.blog.board.domain.vo.Status;
import com.my.blog.board.domain.vo.Title;
import com.my.blog.board.dto.BoardSchCondition;
import com.my.blog.board.dto.request.BoardRequest;
import com.my.blog.board.dto.response.BoardResponse;
import com.my.blog.board.repository.BoardRepository;
import com.my.blog.board.repository.BoardRepositoryImpl;
import com.my.blog.category.entity.Category;
import com.my.blog.category.service.CategoryService;
import com.my.blog.category.vo.CategoryRequest;
import com.my.blog.count.entity.BoardCount;
import com.my.blog.count.entity.BoardCountRepository;
import com.my.blog.count.service.BoardCountService;
import com.my.blog.member.controller.ModelMapper;
import com.my.blog.member.dto.request.CreateRequest;
import com.my.blog.member.entity.Member;
import com.my.blog.member.entity.vo.*;
import com.my.blog.member.repository.MemberRepository;
import com.my.blog.member.service.MemberService;
import com.my.blog.member.service.dto.EntityMapper;
import com.my.blog.member.service.dto.MemberDTO;
import com.my.blog.support.service.UnitTest;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@UnitTest
class BoardServiceTest {

    BoardService boardService;
    BoardSearchService boardSearchService;
  //  BoardCacheService boardCacheService;
    BoardCountService boardCountService;
    @Mock
    MemberRepository memberRepository;

    @Mock
    EntityMapper mapper;

    @Mock
    MemberService memberService;

    @Mock
    CategoryService categoryService;

    @Mock
    BoardRepository boardRepository;

    @Mock
    BoardCountRepository boardCountRepository;
    Long savedMemberId;
    Long savedCategoryId;
    Long savedBoardId;

    @BeforeEach
    public void setUp(){
        //this.boardCacheService = new BoardCacheService();
        this.boardCountService = new BoardCountService(boardCountRepository);
        this.boardService = new BoardService(boardRepository, boardCountService);
    }

    @AfterEach
    public void teardown() {
        boardRepository.deleteAll();
     /*   this.entityManager
                .createNativeQuery("ALTER TABLE board AUTO_INCREMENT = 1")
                .executeUpdate();*/
    }

    @Test
    @DisplayName("상세 보드")
    public void getBoard(){
        //given
        BoardCount countGiven = BoardCount.builder()
                .id(1L)
                .viewCount(0L)
                .build();
        Board given = Board.builder()
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
        BoardSchCondition condition =  BoardSchCondition.builder()
                .title(Title.from("dd"))
                .build()
                ;

        PageRequest pageable = PageRequest.of(0, 10);
        Page<BoardResponse> boards = boardSearchService.getBoards(condition, pageable);

        List<BoardResponse> content = boards.getContent();
        System.out.println("size==" + boards.getTotalElements() + " , " + content.size());

        for (BoardResponse res: content
             ) {
            System.out.println("BoardResponse > "+ res.getId() + ", title=" + res.title());

        }
    }


}