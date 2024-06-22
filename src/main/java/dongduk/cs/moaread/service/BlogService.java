package dongduk.cs.moaread.service;

import dongduk.cs.moaread.dao.BlogDao;
import dongduk.cs.moaread.domain.Blog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogDao blogDao;

    /* 블로그 전체 조회 */
    public List<Blog> getAllBlogList(Integer pageNum, Integer pageSize) {
        return blogDao.getAllBlogList(pageNum, pageSize);
    }

    /* 블로그 전체 개수 조회 */
    public int getAllBlogCount() {
        return blogDao.getAllBlogCount();
    }

    /* 블로그 상세 조회 */
    public Blog getBlog(String userId) {
        return blogDao.getBlogByUserId(userId);
    }
}
