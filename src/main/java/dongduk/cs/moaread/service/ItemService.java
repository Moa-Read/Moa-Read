package dongduk.cs.moaread.service;

import dongduk.cs.moaread.dao.ItemDao;
import dongduk.cs.moaread.domain.Item;
import dongduk.cs.moaread.dto.item.request.CreateItemReqDto;
import dongduk.cs.moaread.dto.item.request.UpdateItemReqDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemDao itemDao;

    @Transactional
    public void createItem(CreateItemReqDto itemReqDto) {
        Item item = new Item();
        item.setBookId(itemReqDto.getBook_isbn());
        item.setState(itemReqDto.getState());
        item.setPrice(itemReqDto.getPrice());
        item.setStock(itemReqDto.getStock());
        itemDao.insertItem(item);
    }

    @Transactional
    public void updateItem(int id, UpdateItemReqDto itemReqDto) {
        Item item = itemDao.findItemById(id);
        item.setState(itemReqDto.getState());
        item.setPrice(itemReqDto.getPrice());
        item.setStock(itemReqDto.getStock());
        itemDao.updateItem(item);
    }

    @Transactional
    public void deleteItem(int id) {
        itemDao.deleteItem(id);
    }

    public Item getItemById(int id) {
        return itemDao.findItemById(id);
    }

    public List<Item> getAllItems() {
        return itemDao.findAllItems();
    }
}
