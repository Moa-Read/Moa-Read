package dongduk.cs.moaread.dao.mybatis;

import dongduk.cs.moaread.dao.ItemDao;
import dongduk.cs.moaread.dao.mybatis.mapper.ItemMapper;
import dongduk.cs.moaread.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MybatisItemDao implements ItemDao {
    @Autowired
    private ItemMapper itemMapper;

    @Override
    public int insertItem(Item item) throws DataAccessException {
        return itemMapper.insertItem(item);
    }

    @Override
    public int updateItem(Item item) throws DataAccessException {
        return itemMapper.updateItem(item);
    }

    @Override
    public int deleteItem(int id) throws DataAccessException {
        return itemMapper.deleteItem(id);
    }

    @Override
    public Item findItemById(int id) throws DataAccessException {
        return itemMapper.findItemById(id);
    }

    @Override
    public List<Item> findAllItems() throws DataAccessException {
        return itemMapper.findAllItems();
    }
}
