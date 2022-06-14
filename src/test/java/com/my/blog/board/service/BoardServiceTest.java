package com.my.blog.board.service;

import com.my.blog.board.domain.Board;
import com.my.blog.board.domain.Status;
import com.my.blog.board.repository.BoardRepositoryImpl;
import com.my.blog.board.vo.BoardSchCondition;
import com.my.blog.board.domain.Content;
import com.my.blog.board.domain.Title;
import com.my.blog.board.vo.request.BoardRequest;
import com.my.blog.board.vo.response.BoardResponse;
import com.my.blog.category.entity.Category;
import com.my.blog.category.service.CategoryService;
import com.my.blog.category.vo.CategoryRequest;
import com.my.blog.count.service.BoardCountService;
import com.my.blog.member.entity.Member;
import com.my.blog.member.service.MemberService;
import com.my.blog.member.entity.vo.Email;
import com.my.blog.member.dto.ModelMapper;
import com.my.blog.member.dto.MemberResponse;
import com.my.blog.member.dto.request.CreateRequest;
import com.my.blog.member.entity.vo.Password;
import org.junit.jupiter.api.*;
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
    BoardCountService boardCountService;

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
                .name("이승은")
                .build();

        this.memberId = memberService.save(ModelMapper.createMember(request));

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
        BoardRequest req = new BoardRequest();
        req.setTitle(Title.of("타이틀"));
        req.setContent(Content.of("contet"));
        req.setStatus(Status.TRUE);
        req.setCategoryId(categoryId);
        req.setMemberId(memberId);

        Board board = req.toEntity();
        MemberResponse mem = memberService.findById(req.getMemberId());

        Category category = categoryService.findById(req.getCategoryId());

        Member member = Member.builder()
                .nickName(mem.getNickName())
                .name(mem.getName())
                .email(mem.getEmail())
                .build();
        board.setMember(member);
        board.setCategory(category);

        Long savedBoardId = boardService.save(board);
        assertThat(savedBoardId).isEqualTo(1);
    }


    @Test
    @Order(2)
    @DisplayName("상세 보드")
    public void getBoard(){
        BoardResponse board = boardService.getBoard(Long.valueOf(1));

        Long viewCount = board.getBoardCount().getViewCount();
        assertThat(board.getId()).isEqualTo(1);
        assertThat(viewCount).isEqualTo(viewCount+1);

    }
    
    @Test
    @Order(3)
    @DisplayName("레파지토리 리스트")
    public void test(){
        BoardSchCondition condition = new BoardSchCondition();
        // condition.setCategory(category);
        //  condition.setTitle(Title.of("타이틀"));

        PageRequest pageable = PageRequest.of(0, 10);
        //Page<BoardResponse> boards = boardService.getBoards(condition, pageable);

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

        BoardSchCondition condition = new BoardSchCondition();
       // condition.setCategory(category);
      //  condition.setTitle(Title.of("타이틀"));

        PageRequest pageable = PageRequest.of(0, 10);
        Page<BoardResponse> boards = boardService.getBoards(condition, pageable);

        List<BoardResponse> content = boards.getContent();

        System.out.println("size==" + boards.getTotalElements() + " , " + content.size());

        for (BoardResponse res: content
             ) {
            System.out.println("BoardResponse > "+ res.getId() + "," + res.title());

        }
    }


}