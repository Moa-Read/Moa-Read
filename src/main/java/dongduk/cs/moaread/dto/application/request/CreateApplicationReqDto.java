package dongduk.cs.moaread.dto.application.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateApplicationReqDto {
    @NotBlank(message = "사용자 ID는 필수 입력값입니다.")
    private String userId;

    @NotNull(message = "총 수량은 필수 입력값입니다.")
    private int totalQuantity;

    @NotBlank(message = "은행명은 필수 입력값입니다.")
    private String accountBank;

    @NotBlank(message = "계좌번호는 필수 입력값입니다.")
    private String accountNumber;
}
