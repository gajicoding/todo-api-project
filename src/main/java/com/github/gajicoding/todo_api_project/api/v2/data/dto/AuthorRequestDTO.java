package com.github.gajicoding.todo_api_project.api.v2.data.dto;

import com.github.gajicoding.todo_api_project.api.v2.data.entity.Author;
import com.github.gajicoding.todo_api_project.api.v2.validation.CreateGroup;
import com.github.gajicoding.todo_api_project.api.v2.validation.DeleteGroup;
import com.github.gajicoding.todo_api_project.api.v2.validation.UpdateGroup;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class AuthorRequestDTO {
    @NotNull(groups={CreateGroup.class}, message = "이름이 입력되지 않았습니다.")
    private String name;

    @NotNull(groups={CreateGroup.class}, message = "이메일이 입력되지 않았습니다.")
    private String email;

    @Setter
    @NotNull(groups={CreateGroup.class, UpdateGroup.class, DeleteGroup.class}, message = "비밀번호가 입력되지 않았습니다.")
    private String password;

    public Author toEntity() {
        return Author.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
