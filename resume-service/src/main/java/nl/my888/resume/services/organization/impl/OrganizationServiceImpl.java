package nl.my888.resume.services.organization.impl;

import javax.persistence.EntityNotFoundException;

import nl.my888.resume.repository.organizations.Organization;
import nl.my888.resume.repository.organizations.OrganizationRepository;
import nl.my888.resume.services.organization.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public Organization findOne(Long id) {
        return organizationRepository.findOne(id);
    }

    @Override
    public Iterable<Organization> findAll() {
        return organizationRepository.findAll();
    }

    @Override
    public Organization save(Organization organization) {
        return organizationRepository.save(organization);
    }

    @Override
    public Organization getOrganization(Long id) {
        final Organization result = findOne(id);
        if (result == null) {
            throw new EntityNotFoundException(String.format("Organization %s not found!", id));
        }
        return result;
    }
}
