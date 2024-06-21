package dongduk.cs.moaread.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
    private int id;
    private String bookId;
    private String state;
    private long price;
    private int stock;
}
