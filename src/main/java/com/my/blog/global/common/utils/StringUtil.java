package com.my.blog.global.common.utils;

import org.springframework.util.StringUtils;

import java.util.Objects;

import static org.springframework.util.StringUtils.*;

public class StringUtil {
    public static String convertToStr(final Object obj, final String str){
        String result = "";
        if( Objects.isNull(obj) || "".equals(obj) ){
            return result = str;
        }
        return result = (String) obj;
    }
}
