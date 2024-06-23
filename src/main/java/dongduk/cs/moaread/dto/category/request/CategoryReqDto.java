package dongduk.cs.moaread.dto.category.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryReqDto {
    @NotBlank(message = "카테고리 이름은 필수 입력값입니다.")
    @Size(max = 5, message = "카테고리 이름은 5자 이내여야 합니다.")
    private String name;
}
