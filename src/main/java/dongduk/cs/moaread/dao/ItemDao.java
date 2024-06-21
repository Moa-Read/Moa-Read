package dongduk.cs.moaread.dao;

import dongduk.cs.moaread.domain.Item;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface ItemDao {
    int insertItem(Item item) throws DataAccessException;

    int updateItem(Item item) throws DataAccessException;

    int deleteItem(int id) throws DataAccessException;

    Item findItemById(int id) throws DataAccessException;

    List<Item> findAllItems() throws DataAccessException;
}
