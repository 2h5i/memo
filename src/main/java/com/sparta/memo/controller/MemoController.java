package com.sparta.memo.controller;

import com.sparta.memo.dto.CreateMemoDto;
import com.sparta.memo.dto.DeleteMemoDto;
import com.sparta.memo.dto.ResponseMemoDto;

import com.sparta.memo.dto.UpdateMemoDto;
import com.sparta.memo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemoController {

    private final MemoService memoService;

    @PostMapping("/memos")
    public ResponseMemoDto createMemo(@RequestBody CreateMemoDto createMemoDto) {
        return memoService.createMemo(createMemoDto);
    }

    @GetMapping("/memos")
    public List<ResponseMemoDto> getMemos() {
        return memoService.getMemoList();
    }

    @GetMapping("/memos/{id}")
    public ResponseMemoDto getMemoById(@PathVariable Long id) {
        return memoService.getMemoById(id);
    }

    @PutMapping("/memos/{id}")
    public ResponseMemoDto updateMemoById(@PathVariable Long id, @RequestBody UpdateMemoDto updateMemoDto) {
        return memoService.updateMemoById(id, updateMemoDto);
    }

    @DeleteMapping("/memos/{id}")
    public HashMap<String,String> deleteMemoById(@PathVariable Long id, @RequestBody DeleteMemoDto deleteMemoDto) {
        return memoService.deleteMemoById(id, deleteMemoDto);
    }

}
