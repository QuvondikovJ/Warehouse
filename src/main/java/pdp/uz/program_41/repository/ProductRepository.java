package pdp.uz.program_41.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pdp.uz.program_41.entity.Measurement;
import pdp.uz.program_41.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
boolean existsProductByNameAndCategoryIdAndActive(String name, Integer categoryId, boolean active);
boolean existsProductByCodeAndActive(String code, boolean active);
boolean existsProductByActive(boolean active);
List<Product> getProductByActive(boolean active);
boolean existsProductByIdAndActive(Integer id, boolean active);

}
