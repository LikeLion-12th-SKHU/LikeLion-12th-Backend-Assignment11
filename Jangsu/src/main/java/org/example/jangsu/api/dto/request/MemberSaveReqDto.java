package org.example.jangsu.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.example.jangsu.domain.Status;

public record MemberSaveReqDto(
        @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        String email,

        @NotBlank(message = "이름을 필수로 입력해야 합니다.")
        @Size(min = 8, max = 50, message = "비밀번호는 8자 이상 입력해주세요.")
        String password,

        @Size(min = 3, max = 20, message = "닉네임은 3자 이상 20자 이하로 입력해주세요.")
        String nickname,

        Status status
) {
}
