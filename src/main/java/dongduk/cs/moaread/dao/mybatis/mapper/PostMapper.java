package dongduk.cs.moaread.dao.mybatis.mapper;

import dongduk.cs.moaread.domain.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    int insertPost(Post post);

    int updatePost(Post post);

    int updateViews(Long id);

    int deletePost(Long id);

    List<Post> getAllPostByUrl(String url);

    List<Post> getAllPostByCategoryId(Long categoryId, String sort, Integer pageNum, Integer pageSize);

    int getAllPostCountByCategoryId(Long categoryId);

    Post getPostById(Long id);

    List<Post> findPostsByBookIsbn(String isbn);
}
