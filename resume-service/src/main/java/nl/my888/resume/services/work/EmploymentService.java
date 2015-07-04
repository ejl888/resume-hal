package nl.my888.resume.services.work;

import nl.my888.resume.repository.work.Employment;

/**
 * Created by ejl on 04/07/15.
 */
public interface EmploymentService {

    Employment save(Employment employment);

    Employment getEmployment(Long personId, Long organizationId);
}
