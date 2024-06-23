package dongduk.cs.moaread.service;

import dongduk.cs.moaread.dao.CategoryDao;
import dongduk.cs.moaread.domain.Category;
import dongduk.cs.moaread.exception.DuplicatedCategoryNameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryDao categoryDao;

    /* 카테고리 생성 */
    public int createCategory(String name, String blogUrl) throws DuplicatedCategoryNameException {
        Category category = new Category();
        category.setName(name);
        category.setBlogUrl(blogUrl);

        // 카테고리 이름 중복 검사
        if (isDuplicated(category)) {
            throw new DuplicatedCategoryNameException();
        }

        return categoryDao.insertCategory(category);
    }

    /* 카테고리 이름 중복 검사 */
    public boolean isDuplicated(Category c) {
        Category category = categoryDao.findCategoryByNameAndUrl(c);

        if (category != null) {
            return true;
        }

        return false;
    }

    /* 카테고리 상세 조회 */
    public Category getCategory(Long id) {
        return categoryDao.findCategoryById(id);
    }

    /* 카테고리 수정 */
    public int updateCategory(Long id, String name, String blogUrl) throws DuplicatedCategoryNameException {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setBlogUrl(blogUrl);

        // 카테고리 이름 중복 검사
        if (isDuplicated(category)) {
            throw new DuplicatedCategoryNameException();
        }

        return categoryDao.updateCategory(category);
    }

    /* 카테고리 삭제 */
    public int deleteCategory(Long id) {
        return categoryDao.deleteCategory(id);
    }

    /* 사용자 카테고리 전체 조회 */
    public List<Category> getCategoryList(String blogUrl) {
        return categoryDao.getAllCategoryByUrl(blogUrl);
    }
}
