package dongduk.cs.moaread.service;

import dongduk.cs.moaread.dao.LikesDao;
import dongduk.cs.moaread.domain.Likes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesDao likesDao;

    @Transactional
    public void likeBook(String userId, String bookIsbn) {
        Likes likes = new Likes();
        likes.setUserId(userId);
        likes.setBookIsbn(bookIsbn);
        likesDao.insertLike(likes);
    }
}
