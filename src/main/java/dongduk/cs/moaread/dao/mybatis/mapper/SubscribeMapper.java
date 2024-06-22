package dongduk.cs.moaread.dao.mybatis.mapper;

import dongduk.cs.moaread.domain.Subscribe;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SubscribeMapper {
    int insertSubscribe(Subscribe subscribe);

    int deleteSubscribe(Subscribe subscribe);

    Subscribe getSubscribeByIdAndUrl(Subscribe subscribe);
}
