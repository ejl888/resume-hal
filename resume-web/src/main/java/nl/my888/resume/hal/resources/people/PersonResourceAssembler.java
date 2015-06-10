package nl.my888.resume.hal.resources.people;

import nl.my888.resume.repository.Person;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PersonResourceAssembler extends ResourceAssemblerSupport<Person, PersonResource> {

    public PersonResourceAssembler() {
        super(PersonResourceController.class, PersonResource.class);
    }

    @Override
    protected PersonResource instantiateResource(Person entity) {
        return new PersonResource(entity.getName());
    }

    @Override
    public PersonResource toResource(Person entity) {
        final PersonResource result = createResourceWithId(entity.getUsername(), entity);

///        result.add(createSelfLink());

        return result;
    }
}
