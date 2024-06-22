package dongduk.cs.moaread.dao;

import dongduk.cs.moaread.domain.Subscribe;
import org.springframework.dao.DataAccessException;

public interface SubscribeDao {
    int insertSubscribe(Subscribe subscribe) throws DataAccessException;

    int deleteSubscribe(Subscribe subscribe) throws DataAccessException;

    Subscribe getSubscribeByIdAndUrl(Subscribe subscribe) throws DataAccessException;
}
