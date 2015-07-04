package nl.my888.resume.services.organization;

import nl.my888.resume.repository.organizations.Organization;

/**
 * Created by ejl on 04/07/15.
 */
public interface OrganizationService {

    Organization findOne(Long id);

    Iterable<Organization> findAll();

    Organization save(Organization organization);
}
