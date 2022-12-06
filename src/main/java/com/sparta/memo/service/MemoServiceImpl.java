package com.sparta.memo.service;

import com.sparta.memo.dto.CreateMemoDto;
import com.sparta.memo.dto.DeleteMemoDto;
import com.sparta.memo.dto.ResponseMemoDto;
import com.sparta.memo.dto.UpdateMemoDto;
import com.sparta.memo.entity.Memo;
import com.sparta.memo.repository.MemoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class MemoServiceImpl implements MemoService{

    private final MemoRepository memoRepository;

    @Override
    public List<ResponseMemoDto> getMemoList() {
        return ResponseMemoDto.of(memoRepository.findAll());
    }

    @Override
    public ResponseMemoDto createMemo(CreateMemoDto createMemoDto) {
        Memo memo = new Memo(createMemoDto);

        memoRepository.save(memo);

        return ResponseMemoDto.of(memo);
    }

    @Override
    public ResponseMemoDto getMemoById(Long id) throws IllegalArgumentException {
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 메모가 없습니다.")
        );

        return ResponseMemoDto.of(memo);
    }

    @Override
    public ResponseMemoDto updateMemoById(Long id, UpdateMemoDto updateMemoDto) {
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 메모가 없습니다.")
        );

        if(memo.isCorrectPassword(updateMemoDto.getPassword())){
            memo.update(updateMemoDto);
            memoRepository.save(memo);
            return ResponseMemoDto.of(memo);
        }else {
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
        }

    }

    @Override
    public HashMap<String,String> deleteMemoById(Long id, DeleteMemoDto deleteMemoDto) throws IllegalArgumentException {
        HashMap<String , String> response= new HashMap<String,String>();
        response.put("status", "ok");

        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 메모가 없습니다.")
        );

        if(memo.isCorrectPassword(deleteMemoDto.getPassword())){
            memoRepository.deleteById(id);
            return response;
        } else {
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
        }
    }
}
