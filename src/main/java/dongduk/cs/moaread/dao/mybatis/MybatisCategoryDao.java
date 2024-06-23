package dongduk.cs.moaread.dao.mybatis;

import dongduk.cs.moaread.dao.CategoryDao;
import dongduk.cs.moaread.dao.mybatis.mapper.CategoryMapper;
import dongduk.cs.moaread.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MybatisCategoryDao implements CategoryDao {
    @Autowired
    private CategoryMapper categoryMapper;

    public int insertCategory(Category category) throws DataAccessException {
        return categoryMapper.insertCategory(category);
    }

    public int updateCategory(Category category) throws DataAccessException {
        return categoryMapper.updateCategory(category);
    }

    public int deleteCategory(Long id) throws DataAccessException {
        return categoryMapper.deleteCategory(id);
    }

    public Category findCategoryByNameAndUrl(Category category) throws DataAccessException {
        return categoryMapper.findCategoryByNameAndUrl(category);
    }

    public Category findCategoryById(Long id) throws DataAccessException {
        return categoryMapper.findCategoryById(id);
    }

    public List<Category> getAllCategoryByUrl(String blogUrl) throws DataAccessException {
        return categoryMapper.getAllCategoryByUrl(blogUrl);
    }
}
