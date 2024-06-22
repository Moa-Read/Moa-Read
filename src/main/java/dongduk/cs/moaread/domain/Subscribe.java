package dongduk.cs.moaread.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Subscribe {
    private String userId;
    private String blogUrl;
    private LocalDateTime createdAt;
}
