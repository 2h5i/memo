package com.sparta.memo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMemoDto {

    private String title;
    private String writer;
    private String contents;
    private String password;

}
