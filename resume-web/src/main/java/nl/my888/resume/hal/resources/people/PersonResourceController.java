package nl.my888.resume.hal.resources.people;

import nl.my888.resume.repository.people.Person;
import nl.my888.resume.services.people.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
@RequestMapping("/people")
@ExposesResourceFor(Person.class)
public class PersonResourceController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonResourceAssembler personResourceAssembler;

    @RequestMapping()
    public ResponseEntity<Resources<PersonResource>> all() {
        final Iterable<Person> users = personService.findAll();

        return new ResponseEntity<>(
                personResourceAssembler.toResourcesForMethod(methodOn(getClass()).all(), users),
                HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PersonResource> get(@PathVariable("id") Long id) {
        final Person person = personService.getPerson(id);

        return new ResponseEntity<>(personResourceAssembler.toResource(person), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonResource> put(
            @PathVariable("id") Long id,
            @RequestBody PersonResource personResource) {
        final Person person = personService.getPerson(id);

        person.setName(personResource.getName());

        final Person savedPerson = personService.save(person);

        return new ResponseEntity<>(personResourceAssembler.toResource(savedPerson), HttpStatus.OK);
    }

}
