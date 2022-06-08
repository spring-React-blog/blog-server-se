package com.my.blog.common.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class ResponseEnvelope<T> {
    ResponseHeader header;
    T body;

}
