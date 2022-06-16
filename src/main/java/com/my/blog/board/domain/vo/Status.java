package com.my.blog.board.domain.vo;

public enum Status {
    TRUE("공개"),FALSE("비공개");

    private String status_desc;

    Status(String desc){
        this.status_desc = desc;
    }
}
