package dongduk.cs.moaread.dto.blog.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogUpdateReqDto {
    @NotBlank(message = "블로그 이름은 필수 입력값입니다.")
    private String name;

    @NotBlank(message = "블로그 소개는 필수 입력값입니다.")
    private String description;
}
