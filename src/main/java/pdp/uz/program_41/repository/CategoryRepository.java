package pdp.uz.program_41.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pdp.uz.program_41.entity.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsCategoryByNameAndParentCategoryIdAndActive(String name, Integer parentCategory_id, boolean active);
boolean existsCategoryByActive(boolean active);
List<Category> getCategoryByActive(boolean active);
boolean existsCategoryByIdAndActive(Integer id, boolean active);

@Query(value = "select count(*) > 0 from category where category.name=:name and category.parent_category_id is null and category.active=:active", nativeQuery=true)
    boolean existsParentCategory(String name, boolean active);
}
