package dongduk.cs.moaread.dto.account.request;

import dongduk.cs.moaread.service.validator.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Password(password = "password", confirmPassword = "confirmPassword")
public class UpdateReqDto {
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()])[a-zA-Z\\d!@#$%^&*()]{8,20}$",
    message = "비밀번호는 8 ~ 20자 이상이어야 하며, 영문 대소문자, 숫자, 특수 문자를 각각 1개 이상 포함해야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 입력값입니다.")
    private String confirmPassword;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    @Pattern(regexp = "^01([016789])-\\d{3,4}-\\d{4}$",
    message = "전화번호 형식에 맞게 입력해주세요.")
    private String phone;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "이메일 형식에 맞게 입력해주세요.")
    private String email;

    @NotBlank(message = "주소는 필수 입력값입니다.")
    private String address;

    @NotBlank(message = "상세 주소는 필수 입력값입니다.")
    private String detailedAddress;

    @Pattern(regexp = "^\\d{5}$", message = "우편번호 형식에 맞게 입력해주세요.")
    private String zip;
}
