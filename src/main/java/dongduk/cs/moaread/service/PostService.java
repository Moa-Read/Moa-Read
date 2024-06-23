package dongduk.cs.moaread.service;

import dongduk.cs.moaread.dao.PostDao;
import dongduk.cs.moaread.domain.Post;
import dongduk.cs.moaread.dto.post.request.PostReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostDao postDao;

    /* 서평 등록 */
    public int createPost(PostReqDto postReqDto) {
        Post post = new Post();
        post.setTitle(postReqDto.getTitle());
        post.setContent(postReqDto.getContent());
        post.setCategoryId(postReqDto.getCategoryId());
        post.setBookIsbn(postReqDto.getBookIsbn());
        post.setView(0L);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        return postDao.insertPost(post);
    }

    /* 서평 수정 */
    public int updatePost(Long id, PostReqDto postReqDto) {
        Post post = new Post();
        post.setId(id);
        post.setTitle(postReqDto.getTitle());
        post.setContent(postReqDto.getContent());
        post.setCategoryId(postReqDto.getCategoryId());
        post.setUpdatedAt(LocalDateTime.now());

        return postDao.updatePost(post);
    }

    /* 서평 삭제 */
    public int deletePost(Long id) {
        return postDao.deletePost(id);
    }

    /* 서평 조회수 증가 */
    public int increaseViews(Long id) {
        return postDao.updateViews(id);
    }

    /* 서평 상세 조회 */
    public Post getPost(Long id) {
        return postDao.getPostById(id);
    }
}
