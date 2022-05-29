package com.my.blog.board.vo.request;

import com.my.blog.board.domain.Board;
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
    BoardRequest req = new BoardRequest();

    @BeforeEach
    public void createBoardRequest(){
        req.setTitle("test1");
        req.setCten("test1..");
    }

    @Test
    public void DtoToEntity(){
        Board board = req.toEntity();
        assertThat(board.getTitle()).isEqualTo(req.getTitle());
    }

    @Test
    public void isTitleUnderMaxSize(){
        req.setTitle("타이틀이 삼십자를 넘으면 에러가 나야한다.");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        // validation 및 출력
        Set<ConstraintViolation<BoardRequest>> validate = validator.validate(req);
        List<String> messages = validate.stream().map(m -> m.getMessage()).collect(toList());

        assertThat(messages.isEmpty()).isTrue();
    }

    @Test
    public void isTitleOverMaxSize(){
        req.setTitle("타이틀이 삼십자를 넘으면 메세지가 나온다. 지금 이 글자는 삼십자 이상이다.");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        // validation 및 출력
        Set<ConstraintViolation<BoardRequest>> validate = validator.validate(req);
        List<String> messages = validate.stream().map(m -> m.getMessage()).collect(toList());

        assertThat(messages.isEmpty()).isFalse();
        assertThat(messages).contains("30자 이하로 입력해주세요.");
        

    }
}