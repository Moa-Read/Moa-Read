package dongduk.cs.moaread.dao.mybatis;

import dongduk.cs.moaread.dao.BookDao;
import dongduk.cs.moaread.domain.Book;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MybatisBookDao implements BookDao {

    private final SqlSession sqlSession;

    private static final String NAMESPACE = "dongduk.cs.moaread.dao.mybatis.mapper.BookMapper";

    @Override
    public int insertBook(Book book) {
        return sqlSession.insert(NAMESPACE + ".insertBook", book);
    }

    @Override
    public Book findByIsbn(String isbn) {
        return sqlSession.selectOne(NAMESPACE + ".findByIsbn", isbn);
    }

    @Override
    public List<Book> searchBooksByKeyword(String keyword) {
        return sqlSession.selectList(NAMESPACE + ".searchBooksByKeyword", keyword);
    }

    @Override
    public List<Book> findTopLikedBooks(int limit) {
        return sqlSession.selectList(NAMESPACE + ".findTopLikedBooks", limit);
    }

    @Override
    public List<Book> findBooksLikedByUser(String userId) {
        return sqlSession.selectList(NAMESPACE + ".findBooksLikedByUser", userId);
    }

    @Override
    public List<Book> findBooksLikedBySimilarUsers(String userId) {
        return sqlSession.selectList(NAMESPACE + ".findBooksLikedBySimilarUsers", userId);
    }
}
