package dongduk.cs.moaread.dao;

import dongduk.cs.moaread.domain.Book;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface BookDao {
    int insertBook(Book book) throws DataAccessException;

    Book findByIsbn(String isbn) throws DataAccessException;

    List<Book> searchBooksByKeyword(String keyword) throws DataAccessException;

    List<Book> findTopLikedBooks(int limit) throws DataAccessException;
}
