package dongduk.cs.moaread.dao;

import dongduk.cs.moaread.domain.Likes;
import org.springframework.dao.DataAccessException;

public interface LikesDao {
    int insertLike(Likes likes) throws DataAccessException;
    int deleteLike(Likes likes) throws DataAccessException;
    int isLiked(Likes likes) throws DataAccessException;
}
