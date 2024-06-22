package dongduk.cs.moaread.service;

import dongduk.cs.moaread.dao.BlogDao;
import dongduk.cs.moaread.dao.SubscribeDao;
import dongduk.cs.moaread.domain.Blog;
import dongduk.cs.moaread.domain.Subscribe;
import dongduk.cs.moaread.dto.blog.request.BlogUpdateReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogDao blogDao;
    private final SubscribeDao subscribeDao;

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

    /* 블로그 구독 확인 */
    public boolean isSubscribed(String userId, String blogUrl) {
        Subscribe subscribe = new Subscribe();
        subscribe.setUserId(userId);
        subscribe.setBlogUrl(blogUrl);

        if (subscribeDao.getSubscribeByIdAndUrl(subscribe) != null) {
            return true;
        }

        return false;
    }

    /* 블로그 수정 */
    public int updateBlog(String url, BlogUpdateReqDto blogUpdateReqDto) {
        Blog blog = new Blog();
        blog.setUrl(url);
        blog.setName(blogUpdateReqDto.getName());
        blog.setDescription(blogUpdateReqDto.getDescription());

        return blogDao.updateBlog(blog);
    }

    /* 블로그 구독 */
    public int subscribe(String userId, String blogUrl) {
        Subscribe newSubscribe = new Subscribe();
        newSubscribe.setUserId(userId);
        newSubscribe.setBlogUrl(blogUrl);
        newSubscribe.setCreatedAt(LocalDateTime.now());

        return subscribeDao.insertSubscribe(newSubscribe);
    }

    /* 블로그 구독 취소 */
    public int unsubscribe(String userId, String blogUrl) {
        Subscribe subscribe = new Subscribe();
        subscribe.setUserId(userId);
        subscribe.setBlogUrl(blogUrl);
        subscribe.setCreatedAt(LocalDateTime.now());

        return subscribeDao.deleteSubscribe(subscribe);
    }
}