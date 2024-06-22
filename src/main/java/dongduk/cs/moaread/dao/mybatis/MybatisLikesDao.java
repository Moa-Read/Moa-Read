package dongduk.cs.moaread.dao.mybatis;

import dongduk.cs.moaread.dao.LikesDao;
import dongduk.cs.moaread.dao.mybatis.mapper.LikesMapper;
import dongduk.cs.moaread.domain.Likes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class MybatisLikesDao implements LikesDao {
    @Autowired
    private LikesMapper likesMapper;

    @Override
    public int insertLike(Likes likes) throws DataAccessException {
        return likesMapper.insertLike(likes);
    }
}
