package com.my.blog.board.service;

import com.my.blog.board.domain.Board;
import com.my.blog.board.domain.vo.Content;
import com.my.blog.board.domain.vo.Status;
import com.my.blog.board.domain.vo.Title;
import com.my.blog.board.dto.BoardSchCondition;
import com.my.blog.board.dto.request.BoardRequest;
import com.my.blog.board.dto.response.BoardResponse;
import com.my.blog.board.repository.BoardRepositoryImpl;
import com.my.blog.category.entity.Category;
import com.my.blog.category.service.CategoryService;
import com.my.blog.category.vo.CategoryRequest;
import com.my.blog.member.dto.ModelMapper;
import com.my.blog.member.dto.request.CreateRequest;
import com.my.blog.member.entity.Member;
import com.my.blog.member.entity.vo.Email;
import com.my.blog.member.entity.vo.Name;
import com.my.blog.member.entity.vo.Password;
import com.my.blog.member.service.MemberService;
import com.querydsl.jpa.impl.JPAQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Rollback(value = false)
class BoardServiceTest {

    @Autowired
    BoardRepositoryImpl repository;

    @Autowired
    BoardService boardService;

    @Autowired
    BoardSearchService boardSearchService;


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
        CreateRequest request = CreateRequest.builder()
                .email(Email.from("test@google.com"))
                .password(Password.from("nono"))
                .name(Name.from("이승은"))
                .build();

        this.memberId = memberService.save(ModelMapper.createMember(request));

    }

    @BeforeEach
    public void createCategory(){
        CategoryRequest categoryRequest = CategoryRequest.builder().name("스프링").build();
        Category category = categoryRequest.toEntity();

        this.categoryId = categoryService.save(category);
    }


    @Test
    @Order(1)
    public void create(){
        BoardRequest req =  BoardRequest.builder()
                .title(Title.from("tt"))
                .content(Content.from("dd"))
                .status(Status.TRUE)
                .categoryId(1L)
                .memberId(1L)
                .build();


        Board board = req.toEntity();
        Member member = memberService.findById(req.getMemberId());
        Category category = categoryService.findById(req.getCategoryId());

        board.setMember(member);
        board.setCategory(category);

        Long savedBoardId = boardService.save(board);
        assertThat(savedBoardId).isEqualTo(1);
    }


    @Test
    @Order(2)
    @DisplayName("상세 보드")
    public void getBoard(){
        Board board = boardService.getBoard(Long.valueOf(1));

        Long viewCount = board.getBoardCount().getViewCount();
        assertThat(board.getId()).isEqualTo(1);
        assertThat(viewCount).isEqualTo(viewCount+1);

    }
    
    @Test
    @Order(3)
    @DisplayName("레파지토리 리스트")
    public void test(){
        BoardSchCondition condition =  BoardSchCondition.builder()
                .title(Title.from("dd"))
                .build()
                ;
        // condition.setCategory(category);
        //  condition.setTitle(Title.of("타이틀"));

        PageRequest pageable = PageRequest.of(0, 10);
        List<BoardResponse> boardList = repository.getBoardList(condition, pageable);
        System.out.println("전체 사이즈 > "+boardList.size());
        for (BoardResponse b : boardList){
            System.out.println("id > "+b.getId() + ", title  = " + b.title());
        }
    }

    @Test
    @Order(4)
    @DisplayName("전체 보드 리스트")
    public void getBoardList(){
      //  Category category = categoryService.findById(categoryId);

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

    @Test
    @DisplayName("count")
    public void count(){
        BoardSchCondition condition =  BoardSchCondition.builder()
                .title(Title.from("dd"))
                .build()
                ;
        JPAQuery<Long> query = repository.countQuery(condition);
        Long count = query.fetchOne();
        System.out.println("count  > "+count);
        assertThat(count).isEqualTo(1);
    }

}