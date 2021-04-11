package pdp.uz.program_41.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pdp.uz.program_41.entity.Measurement;
import pdp.uz.program_41.entity.Supplier;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
boolean existsSupplierByPhoneNumberAndActive(String phoneNumber, boolean active);
boolean existsSupplierByActive(boolean active);
List<Supplier> getSupplierByActive(boolean active);
boolean existsSupplierByIdAndActive(Integer id, boolean active);


}
