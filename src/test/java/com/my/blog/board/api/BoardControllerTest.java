package com.my.blog.board.api;

import com.my.blog.board.domain.vo.Content;
import com.my.blog.board.domain.vo.Status;
import com.my.blog.board.domain.vo.Title;
import com.my.blog.board.dto.request.CreateRequest;
import com.my.blog.board.dto.request.UpdateRequest;
import com.my.blog.board.service.BoardSearchService;
import com.my.blog.board.service.BoardService;
import com.my.blog.category.service.CategoryService;
import com.my.blog.file.dto.ImageDTO;
import com.my.blog.member.entity.vo.RoleType;
import com.my.blog.member.service.MemberService;
import com.my.blog.support.config.AuthUser;
import com.my.blog.support.controller.RestDocsTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BoardControllerTest extends RestDocsTestSupport {
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardSearchService boardSearchService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MemberService memberService;

    private MockMultipartFile image1;

    @BeforeEach
    public void initUploadFile() throws IOException {
        //Mock파일생성
        image1 = new MockMultipartFile("data"
                , "filename.txt"
                , "text/plain"
                , "some xml".getBytes());

    }

    @Test
    @DisplayName("사진 업로드")
    @AuthUser(username = "seung90@gmail.com" , password = "q1w2e3r4", role = RoleType.USER)
    public void upload() throws Exception {
        mockMvc.perform(multipart("/api/upload")
                        .file("file",image1.getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.MULTIPART_FORM_DATA)
                        )
                        ,requestParts(
                                partWithName("file").description("첨부파일")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.APPLICATION_JSON)
                        ),
                        responseFields(
                                fieldWithPath("originalName").description("원본이름").type(JsonFieldType.STRING),
                                fieldWithPath("extension").description("확장자").type(JsonFieldType.STRING),
                                fieldWithPath("savedName").description("저장명").type(JsonFieldType.STRING),
                                fieldWithPath("uploadPath").description("업로드경로").type(JsonFieldType.STRING)
                        )
                ));
    }

    @Test
    @DisplayName("게시물 작성")
    @AuthUser(username = "seung90@gmail.com" , password = "q1w2e3r4", role = RoleType.USER)
    public void save() throws Exception {
        List<ImageDTO> images = new ArrayList<>();
        images.add(ImageDTO.builder().originalName("cat.png").savedName("uuid-uuid-dd.png")
                .extension("png")
                .uploadPath("https://aws..")
                .build());
        CreateRequest request  = CreateRequest.builder()
                .title(Title.from("welcome"))
                .content(Content.from("hello world"))
                .status(Status.TRUE)
                .categoryId(1L)
                .files(images)
                .build();
        String str = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/api/boards")
                        .content(str)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.APPLICATION_JSON)
                        ),
                        requestFields(
                                fieldWithPath("title").description("제목").type(MediaType.APPLICATION_JSON)
                                ,fieldWithPath("content").description("내용").type(MediaType.APPLICATION_JSON)
                                ,fieldWithPath("status").description("공개여부").type(MediaType.APPLICATION_JSON)
                                ,fieldWithPath("categoryId").description("카테고리번호").type(MediaType.APPLICATION_JSON)
                                ,fieldWithPath("files[].originalName").description("파일정보").type(MediaType.APPLICATION_JSON)
                                ,fieldWithPath("files[].extension").description("파일정보").type(MediaType.APPLICATION_JSON)
                                ,fieldWithPath("files[].savedName").description("파일정보").type(MediaType.APPLICATION_JSON)
                                ,fieldWithPath("files[].uploadPath").description("파일정보").type(MediaType.APPLICATION_JSON)
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.APPLICATION_JSON)
                        ),
                        responseFields(
                                fieldWithPath("id").description("아이디").type(JsonFieldType.NUMBER),
                                fieldWithPath("title").description("제목").type(JsonFieldType.STRING),
                                fieldWithPath("content").description("내용").type(JsonFieldType.STRING),
                                fieldWithPath("categoryName").description("카테고리명").type(JsonFieldType.STRING),
                                fieldWithPath("email").description("이메일").type(JsonFieldType.STRING),
                                fieldWithPath("viewCount").description("조회수").type(JsonFieldType.NUMBER)
                        )
                ));
    }

    @Test
    @DisplayName("게시물 작성 에러")
    @AuthUser(username = "seung90@gmail.com" , password = "q1w2e3r4", role = RoleType.USER)
    public void validTestWithException() throws Exception {
        List<ImageDTO> images = new ArrayList<>();
        images.add(ImageDTO.builder().originalName("cat.png").savedName("uuid-uuid-dd.png").extension("png").build());
        CreateRequest request  = CreateRequest.builder()
                .title(Title.from("타이틀이 삼십자를 넘으면 에러가 나야한다.타이틀ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ이 삼십자를 넘으면 에러가 나야한다.타이틀이 삼십자를 넘으면 에러가 나야한다."))
                .content(Content.from("hello world"))
                .status(Status.TRUE)
                .categoryId(1L)
                .files(images)
                .build();
        String str = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/api/boards")
                        .content(str)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.APPLICATION_JSON)
                        ),
                        requestFields(
                                fieldWithPath("title").description("제목").type(MediaType.APPLICATION_JSON)
                                ,fieldWithPath("content").description("내용").type(MediaType.APPLICATION_JSON)
                                ,fieldWithPath("status").description("공개여부").type(MediaType.APPLICATION_JSON)
                                ,fieldWithPath("categoryId").description("카테고리번호").type(MediaType.APPLICATION_JSON)
                                ,fieldWithPath("files[].originalName").description("파일정보").type(MediaType.APPLICATION_JSON)
                                ,fieldWithPath("files[].extension").description("파일정보").type(MediaType.APPLICATION_JSON)
                                ,fieldWithPath("files[].savedName").description("파일정보").type(MediaType.APPLICATION_JSON)
                                ,fieldWithPath("files[].uploadPath").description("파일정보").type(MediaType.APPLICATION_JSON)
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.APPLICATION_JSON)
                        ),
                        responseFields(
                                fieldWithPath("message").description("메세지").type(JsonFieldType.STRING)
                        )
                ));
    }

    @Test
    @DisplayName("게시물 수정")
    @AuthUser(username = "seung90@gmail.com" , password = "q1w2e3r4", role = RoleType.USER)
    public void updateBoard() throws Exception {
        UpdateRequest request  = UpdateRequest.builder()
                .boardId(1L)
                .title(Title.from("welcome"))
                .content(Content.from("hello world"))
                .status(Status.TRUE)
                .categoryId(1L)
                .build();
        String str = objectMapper.writeValueAsString(request);
        mockMvc.perform(put("/api/boards")
                        .content(str)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.APPLICATION_JSON)
                        ),
                        requestFields(
                            fieldWithPath("boardId").description("게시물 번호").type(MediaType.APPLICATION_JSON)
                            ,fieldWithPath("title").description("제목").type(MediaType.APPLICATION_JSON)
                            ,fieldWithPath("content").description("내용").type(MediaType.APPLICATION_JSON)
                            ,fieldWithPath("status").description("공개여부").type(MediaType.APPLICATION_JSON)
                            ,fieldWithPath("categoryId").description("카테고리번호").type(MediaType.APPLICATION_JSON)
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.APPLICATION_JSON)
                        ),
                        responseFields(
                                fieldWithPath("id").description("아이디").type(JsonFieldType.NUMBER),
                                fieldWithPath("title").description("제목").type(JsonFieldType.STRING),
                                fieldWithPath("content").description("내용").type(JsonFieldType.STRING),
                                fieldWithPath("categoryName").description("카테고리명").type(JsonFieldType.STRING),
                                fieldWithPath("email").description("이메일").type(JsonFieldType.STRING),
                                fieldWithPath("viewCount").description("조회수").type(JsonFieldType.NUMBER)
                        )
                ));
    }

    @Test
    @DisplayName("게시물 삭제")
    @AuthUser(username = "seung90@gmail.com" , password = "q1w2e3r4", role = RoleType.USER)
    public void deleteBoard() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/boards/{boardId}","1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.APPLICATION_JSON)
                        ),
                        pathParameters(
                                parameterWithName("boardId").description("게시물번호")
                        )
                ));
    }
    @Test
    @DisplayName("게시물 조회")
    @AuthUser(username = "seung90@gmail.com" , password = "q1w2e3r4", role = RoleType.USER)
    public void getOne() throws Exception {
        mockMvc.perform(
                get("/api/boards/{boardId}","1")
                    )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("seung90@gmail.com"))
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("boardId").description("게시물번호")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.APPLICATION_JSON)
                        ),
                        responseFields(
                                fieldWithPath("id").description("아이디").type(JsonFieldType.NUMBER),
                                fieldWithPath("title").description("타이틀").type(JsonFieldType.STRING),
                                fieldWithPath("content").description("내용").type(JsonFieldType.STRING),
                                fieldWithPath("categoryName").description("카테고리명").type(JsonFieldType.STRING),
                                fieldWithPath("email").description("이메일").type(JsonFieldType.STRING),
                                fieldWithPath("viewCount").description("조회수").type(JsonFieldType.NUMBER)
                        )
                ));
    }

    @Test
    @DisplayName("게시물 목록 조회")
    @AuthUser(username = "seung90@gmail.com" , password = "q1w2e3r4", role = RoleType.USER)
    public void getList() throws Exception {
        mockMvc.perform(
                        get("/api/boards")
                                .param("page", "0")
                                .param("size", "10")
                                .param("sort", "title,DESC")
                                .param("boardId", "1")
                                .param("title", "welcome")
                                .param("content", "hello world")
                                .param("memberEmail", "seung90@gmail.com")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].email").value("seung90@gmail.com"))
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("boardId").description("게시물 번호").optional(),
                                parameterWithName("title").description("제목").optional(),
                                parameterWithName("content").description("내용").optional(),
                                parameterWithName("category").description("카테고리명").optional(),
                                parameterWithName("memberEmail").description("회원 이메일").optional(),
                                parameterWithName("page").description("현재 페이지").optional(),
                                parameterWithName("size").description("게시물 수").optional(),
                                parameterWithName("sort").description("정렬").optional()
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.APPLICATION_JSON)
                        ),
                        responseFields(
                                fieldWithPath("content[].id").description("아이디").type(JsonFieldType.NUMBER),
                                fieldWithPath("content[].title").description("타이틀").type(JsonFieldType.STRING),
                                fieldWithPath("content[].content").description("내용").type(JsonFieldType.STRING),
                                fieldWithPath("content[].categoryName").description("카테고리명").type(JsonFieldType.STRING),
                                fieldWithPath("content[].email").description("이메일").type(JsonFieldType.STRING),
                                fieldWithPath("content[].viewCount").description("조회수").type(JsonFieldType.NUMBER),

                                fieldWithPath("pageable.sort.unsorted").description("현재 페이지"),
                                fieldWithPath("pageable.sort.sorted").description("게시물 수"),
                                fieldWithPath("pageable.sort.empty").description("정렬"),

                                fieldWithPath("sort.unsorted").description("현재 페이지"),
                                fieldWithPath("sort.sorted").description("게시물 수"),
                                fieldWithPath("sort.empty").description("정렬"),

                                fieldWithPath("pageable.pageNumber").description("현재페이지"),
                                fieldWithPath("pageable.pageSize").description("페이지수"),
                                fieldWithPath("pageable.offset").description("시작row번호"),
                                fieldWithPath("pageable.paged").description("페이징여부"),
                                fieldWithPath("pageable.unpaged").description("페이징여부"),

                                fieldWithPath("numberOfElements").description("현재페이지"),
                                fieldWithPath("totalPages").description("현재페이지"),
                                fieldWithPath("totalElements").description("페이지수"),
                                fieldWithPath("last").description("시작row번호"),
                                fieldWithPath("first").description("페이징여부"),
                                fieldWithPath("number").description("페이징여부"),
                                fieldWithPath("size").description("페이징여부"),
                                fieldWithPath("empty").description("페이징여부")
                        )
                ));
    }


}