package dongduk.cs.moaread.dao.mybatis;

import dongduk.cs.moaread.dao.SubscribeDao;
import dongduk.cs.moaread.dao.mybatis.mapper.SubscribeMapper;
import dongduk.cs.moaread.domain.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class MybatisSubscribeDao implements SubscribeDao {
    @Autowired
    private SubscribeMapper subscribeMapper;

    public int insertSubscribe(Subscribe subscribe) throws DataAccessException {
        return subscribeMapper.insertSubscribe(subscribe);
    }

    public int deleteSubscribe(Subscribe subscribe) throws DataAccessException {
        return subscribeMapper.deleteSubscribe(subscribe);
    }

    public Subscribe getSubscribeByIdAndUrl(Subscribe subscribe) throws DataAccessException {
        return subscribeMapper.getSubscribeByIdAndUrl(subscribe);
    }
}
