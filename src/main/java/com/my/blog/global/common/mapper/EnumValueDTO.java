package com.my.blog.global.common.mapper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EnumValueDTO {
    private String key;
    private String value;

    public EnumValueDTO(EnumModel code){
        this.key = code.getKey();
        this.value = code.getValue();
    }
}
