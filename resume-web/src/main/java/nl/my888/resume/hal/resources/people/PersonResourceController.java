package nl.my888.resume.hal.resources.people;

import nl.my888.resume.repository.Person;
import nl.my888.resume.repository.PersonalName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/people")
public class PersonResourceController {

    @Autowired
    private PersonResourceAssembler personResourceAssembler;

    @ResponseBody
    @RequestMapping("/{username}")
    public ResponseEntity<PersonResource> get(@PathVariable("username") String username) {
        final Person person = getPersonByUsername(username);

        return new ResponseEntity<>(personResourceAssembler.toResource(person), HttpStatus.OK);
    }

    private Person getPersonByUsername(String username) {
        final Person person = new Person("ejl888");
        final PersonalName personalName = new PersonalName();
        personalName.setFullName("E. van der Laan");
        person.setName(personalName);
        return person;
    }

    @RequestMapping()
    public ResponseEntity<Void> find() {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
