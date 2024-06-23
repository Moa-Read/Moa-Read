package dongduk.cs.moaread.dto.post.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostReqDto {
    @NotBlank(message = "서평 제목은 필수 입력값입니다.")
    @Size(max = 15)
    private String title;

    @NotBlank(message = "서평 본문은 필수 입력값입니다.")
    @Size(max = 1000, message = "서평 본문은 1000자 이내로 작성해주세요.")
    private String content;

    private Long categoryId;

    @NotBlank(message = "서평 작성 도서를 선택해주세요.")
    private String bookIsbn;
}
