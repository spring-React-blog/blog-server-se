package com.my.blog.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.blog.global.common.mapper.HtmlCharacterEscapes;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class MVCConfig implements WebMvcConfigurer {

    private final ObjectMapper objectMapper;

 /*   @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/docs/**")
                .addResourceLocations("classpath:/static/docs/");
    }
*/
    @Bean
    public MappingJackson2HttpMessageConverter jsonEscapeConverter(){
        ObjectMapper copy = objectMapper.copy();
        copy.getFactory().setCharacterEscapes(new HtmlCharacterEscapes());
        return new MappingJackson2HttpMessageConverter(copy);
    }


}
