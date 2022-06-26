package com.my.blog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations = "/application.properties")
@SpringBootTest
class BlogApplicationTests {

	@Test
	void contextLoads() {
	}

}
