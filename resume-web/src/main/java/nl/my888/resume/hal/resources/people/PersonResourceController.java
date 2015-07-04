package nl.my888.resume.hal.resources.people;

import nl.my888.resume.repository.people.Person;
import nl.my888.resume.services.people.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/people/users")
public class PersonResourceController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonResourceAssembler personResourceAssembler;

    @RequestMapping()
    public ResponseEntity<Resources<PersonResource>> find() {
        final Iterable<Person> users = personService.findAll();

        return new ResponseEntity<>(
                personResourceAssembler.toResourcesForMethod(methodOn(getClass()).find(), users),
                HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<PersonResource> get(@PathVariable("username") String username) {
        final Person person = getUser(username);

        return new ResponseEntity<>(personResourceAssembler.toResource(person), HttpStatus.OK);
    }

    /**
     * PUT, because the request is idempotent.
     * @param username
     * @param personResource
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{username}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonResource> put(
            @PathVariable("username") String username,
            @RequestBody PersonResource personResource) {

        final Person person = getOrCreateUserWithUsername(username);

        person.setName(personResource.getName());

        final Person savedPerson = personService.save(person);

        return new ResponseEntity<>(personResourceAssembler.toResource(savedPerson), HttpStatus.OK);
    }

    private Person getOrCreateUserWithUsername(@PathVariable("username") String username) {
        Person result = personService.findOneByUsername(username);
        if (result == null) {
            result = new Person(username);
        }
        return result;
    }

    private Person getUser(String username) {
        return personService.getPersonByUsername(username);
    }

}
