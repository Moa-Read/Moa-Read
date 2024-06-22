package dongduk.cs.moaread.domain;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class Likes {
    private String userId;
    private String bookIsbn;
    private Timestamp createdAt;
}
