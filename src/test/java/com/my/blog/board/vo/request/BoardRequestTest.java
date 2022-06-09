package com.my.blog.board.vo.request;

import com.my.blog.board.domain.Board;
import com.my.blog.board.vo.Content;
import com.my.blog.board.vo.Title;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
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
        req.setTitle(Title.of("타이틀.."));
        req.setContent(Content.of("내용.."));
    }
    @Test
    public void isTitleUnderMaxSize(){
        req.setTitle(Title.of("타이틀이 삼십자를 넘으면 에러가 나야한다."));


        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        // validation 및 출력
        Set<ConstraintViolation<BoardRequest>> validate = validator.validate(req);
        List<String> messages = validate.stream().map(m -> m.getMessage()).collect(toList());

        assertThat(messages.isEmpty()).isTrue();
    }

    @Test
    public void isTitleOverMaxSize(){
        req.setTitle(Title.of("타이틀이 삼십자를 넘으면 메세지가 나온다. gfgfg지금 이 글자는 삼십자 이상이다.."));

        System.out.println("title > "+req.getTitle().getTitle());
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        // validation 및 출력
        Set<ConstraintViolation<BoardRequest>> validate = validator.validate(req);
        List<String> messages = validate.stream().map(m -> m.getMessage()).collect(toList());
        System.out.println("********");
        for (String  m: messages){
            System.out.println(m);
        }
        //assertThat(messages.isEmpty()).isFalse();
        //assertThat(messages).contains("30자 이하로 입력해주세요.");
        

    }
}