package dongduk.cs.moaread.dao;

import dongduk.cs.moaread.domain.Book;
import org.springframework.dao.DataAccessException;

public interface BookDao {
    int insertBook(Book book) throws DataAccessException;

    Book findByIsbn(String isbn) throws DataAccessException;
}
