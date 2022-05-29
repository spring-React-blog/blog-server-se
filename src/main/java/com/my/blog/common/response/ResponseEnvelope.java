package com.my.blog.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseEnvelope<T> {
    ResponseHeader header;
    T body;

}
