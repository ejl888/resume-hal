package nl.my888.resume.services.work.impl;

import javax.persistence.EntityNotFoundException;

import nl.my888.resume.repository.work.Employment;
import nl.my888.resume.repository.work.EmploymentRepository;
import nl.my888.resume.services.work.EmploymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ejl on 04/07/15.
 */
@Service
@Transactional
public class EmploymentServiceImpl implements EmploymentService {

    @Autowired
    private EmploymentRepository employmentRepository;

    @Override
    public Employment save(Employment employment) {
        return employmentRepository.save(employment);
    }

    @Override
    public Employment getEmployment(Long personId, Long organizationId) {
        final Employment result = employmentRepository.findOneByPersonIdAndOrganizationId(personId, organizationId);
        if (result == null) {
            throw new EntityNotFoundException(String.format("Employment person %s, organization %s not found! ",
                    personId,
                    organizationId));
        }
        return result;
    }
}
