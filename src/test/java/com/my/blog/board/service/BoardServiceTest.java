package com.my.blog.board.service;

import com.my.blog.board.domain.Board;
import com.my.blog.board.domain.Status;
import com.my.blog.board.repository.BoardSearchRepository;
import com.my.blog.board.vo.BoardSchCondition;
import com.my.blog.board.vo.Content;
import com.my.blog.board.vo.Title;
import com.my.blog.board.vo.request.BoardRequest;
import com.my.blog.board.vo.response.BoardResponse;
import com.my.blog.category.entity.Category;
import com.my.blog.category.service.CategoryService;
import com.my.blog.category.vo.CategoryRequest;
import com.my.blog.count.entity.BoardCount;
import com.my.blog.member.entity.Member;
import com.my.blog.member.service.MemberService;
import com.my.blog.member.vo.Email;
import com.my.blog.member.vo.MemberRequest;
import com.my.blog.member.vo.Password;
import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    MemberService memberService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    EntityManager em;

    private Long memberId;
    private Long categoryId;

    @BeforeEach
    public void createMember(){
        MemberRequest request = MemberRequest.builder()
                .email(new Email("test@google.com"))
                .password(new Password("nono"))
                .name("이승은")
                .build();

        this.memberId = memberService.save(request.toEntity());

    }

    @BeforeEach
    public void createCategory(){
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("java");
        Category category = categoryRequest.toEntity();

        this.categoryId = categoryService.save(category);
    }


   @Test
   @Order(1)
    public void create(){
        em.clear();
        BoardRequest req = new BoardRequest();
        req.setTitle(Title.of("타이틀"));
        req.setContent(Content.of("contet"));
        req.setStatus(Status.TRUE);
        req.setCategoryId(categoryId);
        req.setMemberId(memberId);

        Board board = req.toEntity();
        Member member = memberService.findById(req.getMemberId());
        Category category = categoryService.findById(req.getCategoryId());
       // BoardCount count = BoardCount.builder().viewCount(Long.valueOf(0)).build();

        board.setMember(member);
        board.setCategory(category);
     //   board.initBoardCount(count);

        Long savedBoardId = boardService.save(board);
        assertThat(savedBoardId).isEqualTo(1);
        em.clear();
    }

    @Test
    @Order(2)
    @DisplayName("상세 보드")
    public void getBoard(){
        BoardResponse board = boardService.getBoard(Long.valueOf(1));
        System.out.println("getobar > " +  board.title());
    }

    @Test
    @Order(3)
    @DisplayName("전체 보드 리스트")
    public void getBoardList(){


        Category category = categoryService.findById(categoryId);

        BoardSchCondition condition = new BoardSchCondition();
       // condition.setCategory(category);
      //  condition.setTitle(Title.of("타이틀"));

        PageRequest pageable = PageRequest.of(0, 10);
        Page<BoardResponse> boards = boardService.getBoards(condition, pageable);

        int size = boards.getSize();
        List<BoardResponse> content = boards.getContent();
        System.out.println("size==" + size + " , " + content.size());

        for (BoardResponse res: content
             ) {
            System.out.println(res.toString());
        }
    }


}