package com.my.blog.global.common.response;

import com.my.blog.global.common.errorcode.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class ResponseHeader {
    private String code;
    private HttpStatus status;
    private String message;

    private ResponseHeader(ResponseHeaderBuilder builder){
        this.code = builder.getCode();
        this.status = builder.getStatus();
        this.message = builder.getMessage();
    }

    public static ResponseHeaderBuilder builder(){
        return new ResponseHeaderBuilder();
    }

    public static ResponseHeader ok(){
        return new ResponseHeaderBuilder()
                .code("정상")
                .status(HttpStatus.OK)
                .message("success")
                .build();
    }
    public static ResponseHeader error(ErrorCode errorCode){
        return new ResponseHeaderBuilder()
                .code(errorCode.getCode())
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .build();
    }

    @Getter
    public static class ResponseHeaderBuilder {
        private String code;
        private HttpStatus status;
        private String message;

        ResponseHeaderBuilder(){}

        public ResponseHeaderBuilder code(String code){
            this.code = code;
            return this;
        }

        public ResponseHeaderBuilder status(HttpStatus status){
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
