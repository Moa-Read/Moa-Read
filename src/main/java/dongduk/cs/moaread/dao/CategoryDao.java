package dongduk.cs.moaread.dao;

import dongduk.cs.moaread.domain.Category;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface CategoryDao {
    int insertCategory(Category category) throws DataAccessException;

    int updateCategory(Category category) throws DataAccessException;

    int deleteCategory(Long id) throws DataAccessException;

    Category findCategoryByNameAndUrl(Category category) throws DataAccessException;

    Category findCategoryById(Long id) throws DataAccessException;

    List<Category> getAllCategoryByUrl(String blogUrl) throws DataAccessException;
}
