package dongduk.cs.moaread.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Likes {
    private String userId;
    private String bookIsbn;

    public Likes() {}

    public Likes(String userId, String bookIsbn) {
        this.userId = userId;
        this.bookIsbn = bookIsbn;
    }
}
