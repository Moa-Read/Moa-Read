package dongduk.cs.moaread.dao.mybatis.mapper;

import dongduk.cs.moaread.domain.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    int insertCategory(Category category);

    int updateCategory(Category category);

    int deleteCategory(Long id);

    Category findCategoryByNameAndUrl(Category category);

    Category findCategoryById(Long id);

    List<Category> getAllCategoryByUrl(String blogUrl);
}
