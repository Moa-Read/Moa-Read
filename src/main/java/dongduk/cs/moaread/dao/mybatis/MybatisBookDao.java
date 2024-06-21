package dongduk.cs.moaread.dao.mybatis;

import dongduk.cs.moaread.dao.BookDao;
import dongduk.cs.moaread.dao.mybatis.mapper.BookMapper;
import dongduk.cs.moaread.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class MybatisBookDao implements BookDao {
    @Autowired
    private BookMapper bookMapper;

    @Override
    public int insertBook(Book book) throws DataAccessException {
        return bookMapper.insertBook(book);
    }

    @Override
    public Book findByIsbn(String isbn) throws DataAccessException {
        return bookMapper.findByIsbn(isbn);
    }
}
