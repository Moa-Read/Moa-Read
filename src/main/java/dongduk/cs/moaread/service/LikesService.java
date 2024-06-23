package dongduk.cs.moaread.service;

import dongduk.cs.moaread.dao.LikesDao;
import dongduk.cs.moaread.domain.Likes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesDao likesDao;

    public void likeBook(String userId, String bookIsbn) {
        Likes likes = new Likes(userId, bookIsbn);
        likesDao.insertLike(likes);
    }

    public void unlikeBook(String userId, String bookIsbn) {
        Likes likes = new Likes(userId, bookIsbn);
        likesDao.deleteLike(likes);
    }

    public boolean isBookLikedByUser(String userId, String bookIsbn) {
        Likes likes = new Likes(userId, bookIsbn);
        return likesDao.isLiked(likes) > 0;
    }
}
