package nl.my888.resume.hal.resources.organization;

import nl.my888.resume.repository.organizations.Organization;
import nl.my888.springframework.hateoas.links.ProfileLink;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static nl.my888.resume.hal.constants.ProfileUtil.toProfileUri;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class OrganizationResourceAssembler extends ResourceAssemblerSupport<Organization, OrganizationResource> {

    private static final Link COLLECTION_PROFILE_LINK = new ProfileLink(toProfileUri("collection"));

    public OrganizationResourceAssembler() {
        super(OrganizationResourceController.class, OrganizationResource.class);
    }

    @Override
    protected OrganizationResource instantiateResource(Organization entity) {
        return new OrganizationResource(entity.getName());
    }

    @Override
    public OrganizationResource toResource(Organization entity) {
        final OrganizationResource result = createResourceWithId(entity.getId(), entity);

        // no relations yet.

        return result;
    }


    public Resources<OrganizationResource> toResourcesForMethod(Object dummyInvocation, Iterable<Organization> entities) {
        final Resources<OrganizationResource> result = new Resources<>(toResources(entities));

        // metadata links
        result.add(getResourcesProfileLink());
        result.add(linkTo(dummyInvocation).withSelfRel());

        return result;
    }

    public Link getResourcesProfileLink() {
        return COLLECTION_PROFILE_LINK;
    }
}
