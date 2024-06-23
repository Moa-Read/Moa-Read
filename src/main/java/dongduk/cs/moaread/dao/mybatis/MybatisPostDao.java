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

    @Override
    public List<Post> findPostsByBookIsbn(String isbn) throws DataAccessException {
        return postMapper.findPostsByBookIsbn(isbn);
    }

    @Override
    public int insertPost(Post post) throws DataAccessException {
        return postMapper.insertPost(post);
    }

    @Override
    public int updatePost(Post post) throws DataAccessException {
        return postMapper.updatePost(post);
    }

    @Override
    public int updateViews(Long id) throws DataAccessException {
        return postMapper.updateViews(id);
    }

    @Override
    public int deletePost(Long id) throws DataAccessException {
        return postMapper.deletePost(id);
    }

    @Override
    public List<Post> getAllPostByCategoryId(Long categoryId, String sort, Integer pageNum, Integer pageSize) throws DataAccessException {
        return postMapper.getAllPostByCategoryId(categoryId, sort, pageNum, pageSize);
    }

    @Override
    public int getAllPostCountByCategoryId(Long categoryId) throws DataAccessException {
        return postMapper.getAllPostCountByCategoryId(categoryId);
    }

    @Override
    public Post getPostById(Long id) throws DataAccessException {
        return postMapper.getPostById(id);
    }
}
