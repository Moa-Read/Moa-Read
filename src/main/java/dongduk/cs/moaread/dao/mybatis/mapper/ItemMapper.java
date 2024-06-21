package dongduk.cs.moaread.dao.mybatis.mapper;

import dongduk.cs.moaread.domain.Item;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {
    int insertItem(Item item);

    int updateItem(Item item);

    int deleteItem(int id);

    Item findItemById(int id);

    List<Item> findAllItems();
}
