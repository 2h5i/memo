package com.sparta.memo.dto;

import lombok.Getter;

@Getter
public class UpdateMemoDto {

    private String title;
    private String writer;
    private String contents;
    private String password;

}
