package com.sparta.memo.service;

import com.sparta.memo.dto.CreateMemoDto;
import com.sparta.memo.dto.DeleteMemoDto;
import com.sparta.memo.dto.ResponseMemoDto;
import com.sparta.memo.dto.UpdateMemoDto;

import java.util.HashMap;
import java.util.List;

public interface MemoService {

    List<ResponseMemoDto> getMemoList();

    ResponseMemoDto createMemo(CreateMemoDto createMemoDto);

    ResponseMemoDto getMemoById(Long id);

    ResponseMemoDto updateMemoById(Long id, UpdateMemoDto updateMemoDto);

    HashMap<String,String> deleteMemoById(Long id, DeleteMemoDto deleteMemoDto);
}
