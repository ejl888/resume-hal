package nl.my888.resume.hal.resources.employments;

import java.util.Arrays;

import nl.my888.resume.hal.constants.ResumeRelationTypes;
import nl.my888.resume.repository.organizations.Organization;
import nl.my888.resume.repository.people.Person;
import nl.my888.resume.repository.work.Employment;
import nl.my888.springframework.hateoas.links.ProfileLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static nl.my888.resume.hal.constants.ProfileUtil.toProfileUri;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class EmploymentResourceAssembler extends ResourceAssemblerSupport<Employment, EmploymentResource> {

    private static final Link COLLECTION_PROFILE_LINK = new ProfileLink(toProfileUri("collection"));


    @Autowired
    private EntityLinks entityLinks;

    public EmploymentResourceAssembler() {
        super(EmploymentResourceController.class, EmploymentResource.class);
    }

    @Override
    protected EmploymentResource instantiateResource(Employment entity) {
        return new EmploymentResource();
    }

    @Override
    public EmploymentResource toResource(Employment entity) {
        final EmploymentResource result = createResourceWithId(entity.getId(), entity);

        result.add(createLinks(entity));

        return result;
    }

    private Iterable<Link> createLinks(Employment entity) {
        return Arrays.asList(
                createEmployerLink(entity.getEmployer()),
                createEmployeeLink(entity.getEmployee())
        );
    }

    private Link createEmployerLink(Organization employer) {
        return entityLinks.linkForSingleResource(employer.getClass(), employer.getId()).withRel(ResumeRelationTypes.EMPLOYER);
    }

    private Link createEmployeeLink(Person employee) {
        return entityLinks.linkForSingleResource(employee.getClass(), employee.getId()).withRel(ResumeRelationTypes.EMPLOYEE);
    }

    public Resources<EmploymentResource> toResourcesForMethod(Object dummyInvocation, Iterable<Employment> entities) {
        final Resources<EmploymentResource> result = new Resources<>(toResources(entities));

        result.add(getResourcesProfileLink());
        result.add(linkTo(dummyInvocation).withSelfRel());

        return result;
    }

    public Link getResourcesProfileLink() {
        return COLLECTION_PROFILE_LINK;
    }
}
