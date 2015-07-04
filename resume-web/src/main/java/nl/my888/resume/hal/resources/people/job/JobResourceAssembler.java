package nl.my888.resume.hal.resources.people.job;

import nl.my888.resume.repository.work.Employment;
import nl.my888.springframework.hateoas.links.ProfileLink;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static nl.my888.resume.hal.constants.ProfileUtil.toProfileUri;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class JobResourceAssembler extends ResourceAssemblerSupport<Employment, JobResource> {

    private static final Link COLLECTION_PROFILE_LINK = new ProfileLink(toProfileUri("collection"));

    public JobResourceAssembler() {
        super(JobResourceController.class, JobResource.class);
    }

    @Override
    protected JobResource instantiateResource(Employment entity) {
        return new JobResource();
    }

    @Override
    public JobResource toResource(Employment entity) {
        final JobResource result = createResourceWithId(entity.getId(), entity);

        return result;
    }


    public Resources<JobResource> toResourcesForMethod(Object dummyInvocation, Iterable<Employment> entities) {
        final Resources<JobResource> result = new Resources<>(toResources(entities));

        result.add(getResourcesProfileLink());
        result.add(linkTo(dummyInvocation).withSelfRel());

        return result;
    }

    public Link getResourcesProfileLink() {
        return COLLECTION_PROFILE_LINK;
    }
}
