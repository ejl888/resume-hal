package nl.my888.resume.hal.resources.people;

import nl.my888.resume.hal.constants.ResumeRelationTypes;
import nl.my888.resume.hal.resources.people.job.JobResourceController;
import nl.my888.resume.repository.people.Person;
import nl.my888.springframework.hateoas.links.ProfileLink;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static nl.my888.resume.hal.constants.ProfileUtil.toProfileUri;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class PersonResourceAssembler extends ResourceAssemblerSupport<Person, PersonResource> {

    private static final Link COLLECTION_PROFILE_LINK = new ProfileLink(toProfileUri("collection"));

    public PersonResourceAssembler() {
        super(PersonResourceController.class, PersonResource.class);
    }

    @Override
    protected PersonResource instantiateResource(Person entity) {
        return new PersonResource(entity.getName());
    }

    @Override
    public PersonResource toResource(Person entity) {
        // FIXME only valid for users!
        final PersonResource result = createResourceWithId(entity.getUsername(), entity);


        result.add(linkTo(methodOn(JobResourceController.class).jobs(entity.getId())).withRel(ResumeRelationTypes.JOBS));


        return result;
    }


    public Resources<PersonResource> toResourcesForMethod(final Object dummyInvocation, final Iterable<Person> entities) {
        final Resources<PersonResource> result = new Resources<>(toResources(entities));

        result.add(getResourcesProfileLink());
        result.add(linkTo(dummyInvocation).withSelfRel());

        return result;
    }

    public Link getResourcesProfileLink() {
        return COLLECTION_PROFILE_LINK;
    }
}
