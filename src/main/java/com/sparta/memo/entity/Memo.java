package com.sparta.memo.entity;

import com.sparta.memo.dto.CreateMemoDto;
import com.sparta.memo.dto.UpdateMemoDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Memo extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String password;

    public Memo(CreateMemoDto createMemoDto) {
        this.title = createMemoDto.getTitle();
        this.writer = createMemoDto.getWriter();
        this.contents = createMemoDto.getContents();
        this.password = createMemoDto.getPassword();
    }

    public boolean isCorrectPassword(String password) {
        return this.password.equals(password);
    }

    public void update(UpdateMemoDto updateMemoDto) {
        this.title = updateMemoDto.getTitle();
        this.writer = updateMemoDto.getWriter();
        this.contents = updateMemoDto.getContents();
    }

}
