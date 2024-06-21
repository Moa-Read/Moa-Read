package dongduk.cs.moaread.dao.mybatis.mapper;

import dongduk.cs.moaread.domain.Book;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookMapper {
    Book findByIsbn(String isbn);

    int insertBook(Book book);
}
