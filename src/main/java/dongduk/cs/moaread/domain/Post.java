package dongduk.cs.moaread.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Post {
    private Long id;
    private String title;
    private String content;
    private Long categoryId;
    private String bookIsbn;
    private Long view;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
