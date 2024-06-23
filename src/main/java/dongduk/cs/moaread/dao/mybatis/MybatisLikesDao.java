package dongduk.cs.moaread.dao.mybatis;

import dongduk.cs.moaread.dao.LikesDao;
import dongduk.cs.moaread.domain.Likes;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisLikesDao implements LikesDao {

    private final SqlSession sqlSession;

    private static final String NAMESPACE = "dongduk.cs.moaread.dao.mybatis.mapper.LikesMapper";

    @Override
    public int insertLike(Likes likes) {
        return sqlSession.insert(NAMESPACE + ".insertLike", likes);
    }

    @Override
    public int deleteLike(Likes likes) {
        return sqlSession.delete(NAMESPACE + ".deleteLike", likes);
    }

    @Override
    public int isLiked(Likes likes) {
        return sqlSession.selectOne(NAMESPACE + ".isLiked", likes);
    }
}
