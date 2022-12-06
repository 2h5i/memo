package com.sparta.memo.dto;

import com.sparta.memo.entity.Memo;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ResponseMemoDto {

    private Long id;
    private String title;
    private String writer;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ResponseMemoDto(Memo memo) {
        this.id = memo.getId();
        this.title = memo.getTitle();
        this.writer = memo.getWriter();
        this.contents = memo.getContents();
        this.createdAt = memo.getCreatedAt();
        this.modifiedAt = memo.getModifiedAt();
    }

    public static ResponseMemoDto of(Memo memo) {
        return new ResponseMemoDto(memo);
    }

    public static List<ResponseMemoDto> of(List<Memo> memos) {
        return memos.stream().map(ResponseMemoDto::of).collect(Collectors.toList());
    }
}
