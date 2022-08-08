package com.my.blog.board.dto.request;

import com.my.blog.board.domain.vo.Content;
import com.my.blog.board.domain.vo.Status;
import com.my.blog.board.domain.vo.Title;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class BoardRequestTest {


    @BeforeEach
    public void createBoardRequest(){

    }
    @Test
    public void isTitleUnderMaxSize(){
        CreateRequest req = CreateRequest.builder()
                .title(Title.from("타이틀이 삼십자를 넘으면 에러가 나야한다."))
                .content(Content.from("content"))
                .categoryId(1L)
                .status(Status.TRUE)
                .build();

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        // validation 및 출력
        Set<ConstraintViolation<CreateRequest>> validate = validator.validate(req);
        List<String> messages = validate.stream().map(m -> m.getMessage()).collect(toList());

        assertThat(messages.isEmpty()).isTrue();
    }

    @Test
    public void isTitleOverMaxSize(){
        CreateRequest req = CreateRequest.builder()
                .title(Title.from("타이틀이 삼십자를 넘으면 메세지가 나온다. gfgfg지금 이 글자는 삼십자 이상이다.."))
                .content(Content.from("content"))
                .categoryId(1L)
                .status(Status.TRUE)
                .build();

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        // validation 및 출력
        Set<ConstraintViolation<CreateRequest>> validate = validator.validate(req);
        List<String> messages = validate.stream().map(m -> m.getMessage()).collect(toList());

        //assertThat(messages.isEmpty()).isFalse();
        //assertThat(messages).contains("30자 이하로 입력해주세요.");
        

    }
}