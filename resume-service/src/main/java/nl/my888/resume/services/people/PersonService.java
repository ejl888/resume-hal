package nl.my888.resume.services.people;

import java.util.List;

import nl.my888.resume.repository.people.Person;

public interface PersonService {

    Person savePerson(Person person);

    Person getPersonByUsername(String username);

    Iterable<Person> findAll();
}
