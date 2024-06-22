package dongduk.cs.moaread.dao.mybatis;

import dongduk.cs.moaread.dao.BlogDao;
import dongduk.cs.moaread.dao.mybatis.mapper.BlogMapper;
import dongduk.cs.moaread.domain.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MybatisBlogDao implements BlogDao {
    @Autowired
    private BlogMapper blogMapper;

    public int insertBlog(Blog blog) throws DataAccessException {
        return blogMapper.insertBlog(blog);
    }

    public List<Blog> getAllBlogList(Integer pageNum, Integer pageSize) throws DataAccessException {
        return blogMapper.getAllBlogList(pageNum, pageSize);
    }

    public int getAllBlogCount() throws DataAccessException {
        return blogMapper.getAllBlogCount();
    }

    public Blog getBlogByUserId(String id) throws DataAccessException {
        return blogMapper.getBlogByUserId(id);
    }
}
