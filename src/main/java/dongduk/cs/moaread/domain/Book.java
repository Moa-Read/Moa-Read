package dongduk.cs.moaread.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Book {
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private Date publishDate;
    private String description;
    private int price;
<<<<<<< Updated upstream
    private byte[] image;
=======
    private String image;
>>>>>>> Stashed changes
}
