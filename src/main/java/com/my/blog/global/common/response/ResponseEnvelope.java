package com.my.blog.global.common.response;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class ResponseEnvelope<T> {
    ResponseHeader header;
    T body;

}
