package dongduk.cs.moaread.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class Book {
    private String isbn;
    private String title;
    private String author;
    private String translator;
    private String publisher;
    private Date publishDate;
    private String description;
    private int price;
    private String image;  // 이미지 URL을 저장
}
