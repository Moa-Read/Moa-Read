package dongduk.cs.moaread.dto.item.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateItemReqDto {
    @NotBlank(message = "상태는 필수 입력값입니다.")
    private String state;

    @NotBlank(message = "가격은 필수 입력값입니다.")
    private long price;

    @NotBlank(message = "재고는 필수 입력값입니다.")
    private int stock;
}
