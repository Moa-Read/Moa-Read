package dongduk.cs.moaread.dao.mybatis.mapper;

import dongduk.cs.moaread.domain.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogMapper {
    int insertBlog(Blog blog);

    List<Blog> getAllBlogList(Integer pageNum, Integer pageSize);

    int getAllBlogCount();
}
