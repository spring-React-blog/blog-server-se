package com.my.blog.category.service;

import com.my.blog.category.entity.Category;
import com.my.blog.category.repository.CategoryRepository;
import com.my.blog.category.vo.CreateRequest;
import com.my.blog.support.service.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@UnitTest
class CategoryServiceTest {

    @Mock
    CategoryService categoryService;

   /* @Autowired
    EntityManager entityManager;*/

    @Mock
    CategoryRepository categoryRepository;

   /* @BeforeEach
    public void init() {
        categoryRepository.deleteAll();
        this.entityManager
                .createNativeQuery("ALTER TABLE category AUTO_INCREMENT = 1")
                .executeUpdate();
    }

    @AfterEach
    public void teardown() {
        categoryRepository.deleteAll();
        this.entityManager
                .createNativeQuery("ALTER TABLE category AUTO_INCREMENT = 1")
                .executeUpdate();
    }
*/
    @Test
    @DisplayName("카테고리 생성")
    public void create(){
        CreateRequest createRequest = CreateRequest.builder().name("스프링").build();
        Category category = createRequest.toEntity();
        given(categoryService.save(category)).willReturn(1L);
        Long savedId = categoryService.save(category);
        assertThat(savedId).isEqualTo(1L);

    }

}