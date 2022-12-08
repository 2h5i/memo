package com.sparta.memo.controller;

import com.sparta.memo.dto.CreateMemoDto;
import com.sparta.memo.dto.DeleteMemoDto;
import com.sparta.memo.dto.ResponseMemoDto;
import com.sparta.memo.dto.UpdateMemoDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemoControllerTest {

    @Autowired
    MemoController memoController;

    @DisplayName("1.메모 작성")
    @Test
    void createMemo() {
        // Given
        CreateMemoDto createMemoDto = new CreateMemoDto();
        createMemoDto.setTitle("제목");
        createMemoDto.setWriter("작성자");
        createMemoDto.setContents("내용");
        createMemoDto.setPassword("1234");

        // When
        ResponseMemoDto response = memoController.createMemo(createMemoDto);

        // Then
        assertThat(response.getTitle()).isEqualTo(createMemoDto.getTitle());
        assertThat(response.getContents()).isEqualTo(createMemoDto.getContents());
        assertThat(response.getWriter()).isEqualTo(createMemoDto.getWriter());
        assertThat(response.getId()).isNotNull();
        assertThat(response.getCreatedAt()).isEqualTo(response.getModifiedAt());
    }

    @DisplayName("2. 메모 단건 조회")
    @Test
    void findMemoById() {
        // Given
        CreateMemoDto createMemoDto = new CreateMemoDto();
        createMemoDto.setTitle("제목");
        createMemoDto.setWriter("작성자");
        createMemoDto.setContents("내용");
        createMemoDto.setPassword("1234");

        ResponseMemoDto response = memoController.createMemo(createMemoDto);

        // When
        ResponseMemoDto findMemo = memoController.getMemoById(response.getId());

        // Then
        assertThat(findMemo.getTitle()).isEqualTo(createMemoDto.getTitle());
        assertThat(findMemo.getWriter()).isEqualTo(createMemoDto.getWriter());
        assertThat(findMemo.getContents()).isEqualTo(createMemoDto.getContents());
        assertThat(findMemo.getId()).isEqualTo(response.getId());
    }

    @DisplayName("3. 메모 단건 조회 - 없는 메모")
    @Test
    void findNotExistMemo() {
        assertThatIllegalArgumentException().isThrownBy(()->memoController.getMemoById(Long.MAX_VALUE));
    }

    @DisplayName("4. 게시글 전체 조화")
    @Test
    void findMemos() {
        // Given
        CreateMemoDto createMemoDto = new CreateMemoDto();
        createMemoDto.setTitle("제목");
        createMemoDto.setWriter("작성자");
        createMemoDto.setContents("내용");
        createMemoDto.setPassword("1234");
        int beforeSize = memoController.getMemos().size();
        memoController.createMemo(createMemoDto);

        // When
        List<ResponseMemoDto> response = memoController.getMemos();
        int afterSize = response.size();

        // Then
        assertThat(response.stream().allMatch(Objects::nonNull)).isTrue();
        assertThat(beforeSize).isEqualTo(afterSize-1);
    }

    @DisplayName("5. 메모 수정 - 정상 로직")
    @Test
    void updateMemo() throws InterruptedException {
        // Given
        CreateMemoDto createMemoDto = new CreateMemoDto();
        createMemoDto.setTitle("제목");
        createMemoDto.setWriter("작성자");
        createMemoDto.setContents("내용");
        createMemoDto.setPassword("1234");

        ResponseMemoDto createdMemo = memoController.createMemo(createMemoDto);

        UpdateMemoDto updateMemoDto = new UpdateMemoDto();
        updateMemoDto.setTitle("제목123");
        updateMemoDto.setWriter("작성자123");
        updateMemoDto.setContents("내용123");
        updateMemoDto.setPassword("1234");

        // When
        ResponseMemoDto editedMemo = memoController.updateMemoById(createdMemo.getId(), updateMemoDto);

        // Then
        assertThat(editedMemo.getTitle()).isEqualTo("제목123");
        assertThat(editedMemo.getWriter()).isEqualTo("작성자123");
        assertThat(editedMemo.getContents()).isEqualTo("내용123");
        assertThat(editedMemo.getId()).isEqualTo(createdMemo.getId());
        assertThat(editedMemo.getModifiedAt()).isAfter(editedMemo.getCreatedAt());
    }

    @DisplayName("6. 메모 수정 - 잘못된 비밀번호")
    @Test
    void updateMemoWrongPwd() {
        // Given
        CreateMemoDto createMemoDto = new CreateMemoDto();
        createMemoDto.setTitle("제목");
        createMemoDto.setWriter("작성자");
        createMemoDto.setContents("내용");
        createMemoDto.setPassword("1234");

        ResponseMemoDto createdMemo = memoController.createMemo(createMemoDto);

        UpdateMemoDto updateMemoDto = new UpdateMemoDto();
        updateMemoDto.setTitle("제목123");
        updateMemoDto.setWriter("작성자123");
        updateMemoDto.setContents("내용123");
        updateMemoDto.setPassword("4321");

        // When-Then
        assertThatIllegalArgumentException().isThrownBy(()->memoController.updateMemoById(createdMemo.getId(), updateMemoDto))
                .withMessage("비밀번호가 맞지 않습니다.");
    }

    @DisplayName("7. 게시글 수정 - 없는 게시글")
    @Test
    void updateMemoNoMemo() {
        // Given
        Long wrongMemoId = Long.MAX_VALUE;
        UpdateMemoDto updateMemoDto = new UpdateMemoDto();
        updateMemoDto.setTitle("제목123");
        updateMemoDto.setWriter("작성자123");
        updateMemoDto.setContents("내용123");
        updateMemoDto.setPassword("4321");

        // When-Then
        assertThatIllegalArgumentException().isThrownBy(()->memoController.updateMemoById(wrongMemoId, updateMemoDto))
                .withMessage("해당하는 메모가 없습니다.");
    }

    @DisplayName("8. 게시글 삭제  - 정상 로직")
    @Test
    void deleteMemo() {
        // Given
        CreateMemoDto createMemoDto = new CreateMemoDto();
        createMemoDto.setTitle("제목");
        createMemoDto.setWriter("작성자");
        createMemoDto.setContents("내용");
        createMemoDto.setPassword("1234");

        ResponseMemoDto createdMemo = memoController.createMemo(createMemoDto);

        DeleteMemoDto deleteMemoDto = new DeleteMemoDto();
        deleteMemoDto.setPassword("1234");

        int beforeSize = memoController.getMemos().size();

        // When
        Map<String,String> response = memoController.deleteMemoById(createdMemo.getId(), deleteMemoDto);
        int afterSize= memoController.getMemos().size();

        // Then
        assertThat(response.get("status")).isEqualTo("ok");
        assertThat(beforeSize).isEqualTo(afterSize+1);
        assertThatIllegalArgumentException().isThrownBy(()->memoController.getMemoById(createdMemo.getId()))
                .withMessage("해당하는 메모가 없습니다.");
    }

    @DisplayName("9. 메모 삭제  - 잘못된 비밀번호")
    @Test
    void deleteMemoWrongPwd() {
        // Given
        CreateMemoDto createMemoDto = new CreateMemoDto();
        createMemoDto.setTitle("제목");
        createMemoDto.setWriter("작성자");
        createMemoDto.setContents("내용");
        createMemoDto.setPassword("1234");

        ResponseMemoDto createdMemo = memoController.createMemo(createMemoDto);

        DeleteMemoDto deleteMemoDto = new DeleteMemoDto();
        deleteMemoDto.setPassword("4321");

        int beforeSize = memoController.getMemos().size();

        // When - Then
        assertThatIllegalArgumentException().isThrownBy(()->memoController.deleteMemoById(createdMemo.getId(), deleteMemoDto))
                .withMessage("비밀번호가 맞지 않습니다.");
        int afterSize = memoController.getMemos().size();
        assertThat(beforeSize).isEqualTo(afterSize);
        assertThatNoException().isThrownBy(()->memoController.getMemoById(createdMemo.getId()));

    }

    @DisplayName("10. 메모 삭제  - 없는 메모")
    @Test
    void deleteMemoNotExistMemo() {
        // Given
        Long memoId = Long.MAX_VALUE;

        DeleteMemoDto deleteMemoDto = new DeleteMemoDto();
        deleteMemoDto.setPassword("1234");

        int beforeSize = memoController.getMemos().size();

        // When - Then
        assertThatIllegalArgumentException().isThrownBy(()->memoController.deleteMemoById(memoId,deleteMemoDto))
                .withMessage("해당하는 메모가 없습니다.");
        int afterSize = memoController.getMemos().size();
        assertThat(beforeSize).isEqualTo(afterSize);
    }

}