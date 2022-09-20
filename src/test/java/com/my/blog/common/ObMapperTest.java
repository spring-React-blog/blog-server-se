package com.my.blog.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.my.blog.board.domain.vo.Content;
import com.my.blog.board.domain.vo.Status;
import com.my.blog.board.domain.vo.Title;
import com.my.blog.board.dto.request.CreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ObMapperTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void 태그치환() throws JsonProcessingException {
        String content1 = "<li>content</li>";
        String expected = "&lt;li&gt;content&lt;/li&gt;";

        CreateRequest request = CreateRequest.builder()
                .title(Title.from("dd"))
                .content(Content.from(content1))
                .categoryId(1L)
                .status(Status.TRUE)
                .build();

        ResponseEntity<CreateRequest> response = restTemplate.postForEntity(
                "/api/public/xss",
                request,
                CreateRequest.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getContent().content()).isEqualTo(expected);
    }
    @Test
    public void application_form_전송() {
        String content = "<li>content</li>";
        String expected = "&lt;li&gt;content&lt;/li&gt;";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("content", content);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<CreateRequest> response = restTemplate.exchange("/api/public/form",
                HttpMethod.POST,
                entity,
                CreateRequest.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getContent().content()).isEqualTo(expected);
    }
}
