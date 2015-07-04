package nl.my888.resume.repository.work;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by ejl on 04/07/15.
 */
public interface EmploymentRepository extends CrudRepository<Employment, Long> {

    Employment findOneByEmployeeIdAndEmployerId(Long personId, Long organizationId);

    Iterable<Employment> findEmploymentsByEmployeeId(Long personId);
}
