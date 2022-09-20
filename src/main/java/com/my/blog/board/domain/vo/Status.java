package com.my.blog.board.domain.vo;

import com.my.blog.global.common.mapper.EnumModel;
public enum Status implements EnumModel {
    TRUE("공개"),FALSE("비공개"), DELETED("삭제");

    private String statusDesc;

    Status(String desc){
        this.statusDesc = desc;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return statusDesc;
    }
}
