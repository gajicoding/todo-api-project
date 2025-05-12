package com.github.gajicoding.todo_api_project.api.v2.data.dto;

import com.github.gajicoding.todo_api_project.api.v2.data.entity.Author;
import com.github.gajicoding.todo_api_project.api.v2.data.entity.Todo;
import com.github.gajicoding.todo_api_project.api.v2.validation.CreateGroup;
import com.github.gajicoding.todo_api_project.api.v2.validation.DeleteGroup;
import com.github.gajicoding.todo_api_project.api.v2.validation.UpdateGroup;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoRequestDTO {
    @NotNull(groups = {CreateGroup.class}, message = "제목이 입력되지 않았습니다.")
    @Size(groups = {CreateGroup.class, UpdateGroup.class}, max = 50, message = "제목은 최대 50자까지 입력 가능합니다.")
    private String title;

    @NotNull(groups = {CreateGroup.class}, message = "내용이 입력되지 않았습니다.")
    @Size(groups = {CreateGroup.class, UpdateGroup.class}, max = 200, message = "내용은 최대 200자까지 입력 가능합니다.")
    private String contents;

    @NotNull(groups = {CreateGroup.class, UpdateGroup.class, DeleteGroup.class}, message = "작성자 ID가 입력되지 않았습니다.")
    private Long authorId;

    @NotNull(groups = {CreateGroup.class, UpdateGroup.class, DeleteGroup.class}, message = "비밀번호가 입력되지 않았습니다.")
    private String password;

    public Todo toEntity() {
        return Todo.builder()
                .title(title)
                .contents(contents)
                .author(new Author(authorId))
                .build();
    }
}
