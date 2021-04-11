package pdp.uz.program_41.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pdp.uz.program_41.entity.Currency;
import pdp.uz.program_41.entity.Measurement;

import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
boolean existsCurrencyByNameAndActive(String name, boolean active);
boolean existsCurrencyByActive(boolean active);
List<Currency> getCurrencyByActive(boolean active);
boolean existsCurrencyByIdAndActive(Integer id, boolean active);
}
