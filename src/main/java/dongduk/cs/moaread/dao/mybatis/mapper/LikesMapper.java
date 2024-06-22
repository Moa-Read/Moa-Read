package dongduk.cs.moaread.dao.mybatis.mapper;

import dongduk.cs.moaread.domain.Likes;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikesMapper {
    int insertLike(Likes likes);
}
