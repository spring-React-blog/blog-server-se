package com.my.blog.board.service;

import com.my.blog.board.domain.Board;
import com.my.blog.board.domain.Status;
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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

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
    public void create(){
        em.clear();
        BoardRequest req = new BoardRequest();
        req.setTitle(new Title("타이틀"));
        req.setContent(new Content("contet"));
        req.setStatus(Status.TRUE);
        req.setCategoryId(categoryId);
        req.setMemberId(memberId);


        Board board = req.toEntity();
        Member member = memberService.findById(req.getMemberId());
        Category category = categoryService.findById(req.getCategoryId());
        BoardCount count = new BoardCount();

        board.setMember(member);
        board.setCategory(category);
        board.initBoardCount(count);

        Long savedBoardId = boardService.save(board);
        assertThat(savedBoardId).isEqualTo(2);
        em.clear();
    }

    @Test
    public void findById(){
        long before = System.currentTimeMillis();
        BoardResponse board = boardService.findByBoardId(Long.valueOf(2));
        assertThat(board.getCategoryName()).isEqualTo("java");
        assertThat(board.getTitle().getTitle()).isEqualTo("타이틀");
        System.out.println("board.getViewCount() > "+board.getViewCount());
        assertThat(board.getViewCount()).isEqualTo(0);
        long end = System.currentTimeMillis();
        System.out.println("findby" + (end-before));
    }
    @Test
    public void findById2(){
        long before = System.currentTimeMillis();
        BoardResponse board = boardService.findByBoardId2(Long.valueOf(2));
        assertThat(board.getCategoryName()).isEqualTo("java");
        assertThat(board.getTitle().getTitle()).isEqualTo("타이틀");
        assertThat(board.getViewCount()).isEqualTo(0);
        long end = System.currentTimeMillis();
        System.out.println("findby2" + (end-before));
    }
    @Test
    public void findById3(){
        long before = System.currentTimeMillis();
        BoardResponse board = boardService.findByBoardId3(Long.valueOf(2));
        assertThat(board.getCategoryName()).isEqualTo("java");
        assertThat(board.getTitle().getTitle()).isEqualTo("타이틀");
        assertThat(board.getViewCount()).isEqualTo(0);
        long end = System.currentTimeMillis();
        System.out.println("findby2" + (end-before));
    }
    @Test
    public void findById4(){
        long before = System.currentTimeMillis();
        BoardResponse board = boardService.findByBoardId4(Long.valueOf(2));
        assertThat(board.getCategoryName()).isEqualTo("java");
        assertThat(board.getTitle().getTitle()).isEqualTo("타이틀");
        assertThat(board.getViewCount()).isEqualTo(0);
        long end = System.currentTimeMillis();
        System.out.println("findby2" + (end-before));
    }
}