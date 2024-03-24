package kr.co.chunjae.user.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserRequestDTO {

    @Size(min=13, max=13, message = "- 기호를 포함하여 13자리를 입력해야합니다.")
    @NotBlank(message = "핸드폰번호는 필수 입력 값입니다.")
    private String phone;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Size(min = 2, message = "이름은 최소 2글자 이상 입력해주세요.")
    @Pattern(regexp = "^[가-힣]*$", message = "한글 이름만 입력해주세요.")
    private String name;

    @Size(min = 4, max=12, message = "비밀번호는 최소 4자리 이상 입력해주세요")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotNull(message = "학년은 필수 선택 값입니다.")
    private Integer grade;
}
