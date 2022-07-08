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
import com.my.blog.member.controller.ModelMapper;
import com.my.blog.member.dto.request.CreateRequest;
import com.my.blog.member.entity.vo.Email;
import com.my.blog.member.entity.vo.Name;
import com.my.blog.member.entity.vo.Password;
import com.my.blog.member.service.MemberService;
import com.my.blog.member.service.dto.EntityMapper;
import com.my.blog.member.service.dto.MemberDTO;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardSearchService boardSearchService;

    @Autowired
    MemberService memberService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    EntityManager entityManager;


    Long savedMemberId;
    Long savedCategoryId;
    Long savedBoardId;

    @BeforeEach
    public void init(){
        //회원 mock 정의
        CreateRequest request = CreateRequest.builder()
                .email(Email.from("test@google.com"))
                .password(Password.from("nono"))
                .name(Name.from("이승은"))
                .build();
        MemberDTO member = ModelMapper.createMember(request);
        savedMemberId = memberService.save(member);

        //카테고리 mock 정의
        CategoryRequest categoryRequest = CategoryRequest.builder().name("스프링").build();
        Category category = categoryRequest.toEntity();
        savedCategoryId = categoryService.save(category);

        //보드생성
        BoardRequest req =  BoardRequest.builder()
                .title(Title.from("tt"))
                .content(Content.from("dd"))
                .status(Status.TRUE)
                .categoryId(savedCategoryId)
                .memberId(savedMemberId)
                .build();

        Board board = req.toEntity();
        MemberDTO memberDTO = memberService.findById(req.getMemberId());

        board.setMember(EntityMapper.toEntity(memberDTO));
        board.setCategory(category);

        savedBoardId = boardService.save(board);

    }
    @AfterEach
    public void teardown() {
        boardRepository.deleteAll();
        this.entityManager
                .createNativeQuery("ALTER TABLE board AUTO_INCREMENT = 1")
                .executeUpdate();
    }
    @Test
    @DisplayName("보드 생성")
    public void create(){
        BoardRequest req =  BoardRequest.builder()
                .title(Title.from("tt"))
                .content(Content.from("dd"))
                .status(Status.TRUE)
                .categoryId(savedCategoryId)
                .memberId(savedMemberId)
                .build();


        Board board = req.toEntity();
        MemberDTO memberDTO = memberService.findById(req.getMemberId());
        Category category = categoryService.findById(req.getCategoryId());

        board.setMember(EntityMapper.toEntity(memberDTO));
        board.setCategory(category);

        Long savedBoardId = boardService.save(board);
        assertThat(savedBoardId).isEqualTo(2L);
    }


    @Test
    @DisplayName("상세 보드")
    public void getBoard(){
        Board board = boardService.getBoard(savedBoardId);

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