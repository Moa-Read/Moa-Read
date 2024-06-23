package dongduk.cs.moaread.dao;

import dongduk.cs.moaread.domain.Post;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface PostDao {
    int insertPost(Post post) throws DataAccessException;

    int updatePost(Post post) throws DataAccessException;

    int updateViews(Long id) throws DataAccessException;

    int deletePost(Long id) throws DataAccessException;

    List<Post> getAllPostByUrl(String url) throws DataAccessException;

    List<Post> getAllPostByCategoryId(Long categoryId, String sort, Integer pageNum, Integer pageSize) throws DataAccessException;

    int getAllPostCountByCategoryId(Long categoryId) throws DataAccessException;

    Post getPostById(Long id) throws DataAccessException;

    List<Post> findPostsByBookIsbn(String isbn) throws DataAccessException;
}
