package dongduk.cs.moaread.dao.mybatis;

import dongduk.cs.moaread.dao.PostDao;
import dongduk.cs.moaread.dao.mybatis.mapper.PostMapper;
import dongduk.cs.moaread.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MybatisPostDao implements PostDao {
    @Autowired
    private PostMapper postMapper;

    public int insertPost(Post post) throws DataAccessException {
        return postMapper.insertPost(post);
    }

    public int updatePost(Post post) throws DataAccessException {
        return postMapper.updatePost(post);
    }

    public int updateViews(Long id) throws DataAccessException {
        return postMapper.updateViews(id);
    }

    public int deletePost(Long id) throws DataAccessException {
        return postMapper.deletePost(id);
    }

    public List<Post> getAllPostByUrl(String url) throws DataAccessException {
        return postMapper.getAllPostByUrl(url);
    }

    public List<Post> getAllPostByCategoryId(Long categoryId, String sort, Integer pageNum, Integer pageSize) throws DataAccessException {
        return postMapper.getAllPostByCategoryId(categoryId, sort, pageNum, pageSize);
    }

    public int getAllPostCountByCategoryId(Long categoryId) throws DataAccessException {
        return postMapper.getAllPostCountByCategoryId(categoryId);
    }

    public Post getPostById(Long id) throws DataAccessException {
        return postMapper.getPostById(id);
    }
}
