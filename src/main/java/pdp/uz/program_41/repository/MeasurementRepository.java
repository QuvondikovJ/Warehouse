package pdp.uz.program_41.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pdp.uz.program_41.entity.Measurement;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    boolean existsMeasurementByActive(boolean active);
    List<Measurement> getMeasurementByActive(boolean active);
    boolean existsMeasurementByIdAndActive(Integer id, boolean active);
    boolean existsMeasurementByNameAndActive(String name, boolean active);
}
