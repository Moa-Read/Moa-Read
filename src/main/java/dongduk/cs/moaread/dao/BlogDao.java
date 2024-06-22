package dongduk.cs.moaread.dao;

import dongduk.cs.moaread.domain.Blog;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface BlogDao {
    int insertBlog(Blog blog) throws DataAccessException;

    List<Blog> getAllBlogList(Integer pageNum, Integer pageSize) throws DataAccessException;

    int getAllBlogCount() throws DataAccessException;
}
