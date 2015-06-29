package nl.my888.resume.repository.organizations;

import org.springframework.data.repository.CrudRepository;

public interface OrganizationRepository extends CrudRepository<Organization, Long> {

    Organization findByName(String name);
}
