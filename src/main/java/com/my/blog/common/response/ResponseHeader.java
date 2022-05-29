package com.my.blog.common.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ResponseHeader {
    private String code;
    private String status;
    private String message;

    private ResponseHeader(ResponseHeaderBuilder builder){
        this.code = builder.getCode();
        this.status = builder.getStatus();
        this.message = builder.getMessage();
    }

    public static ResponseHeaderBuilder builder(){
        return new ResponseHeaderBuilder();
    }

    @Getter
    public static class ResponseHeaderBuilder {
        private String code;
        private String status;
        private String message;

        ResponseHeaderBuilder(){}

        public ResponseHeaderBuilder code(String code){
            this.code = code;
            return this;
        }

        public ResponseHeaderBuilder status(String status){
            this.status = status;
            return this;
        }

        public ResponseHeaderBuilder message(String message){
            this.message = message;
            return this;
        }
        public ResponseHeader build(){
            return new ResponseHeader(this);
        }
    }

}
