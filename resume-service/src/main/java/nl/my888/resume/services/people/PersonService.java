package nl.my888.resume.services.people;

import nl.my888.resume.repository.people.Person;

public interface PersonService {

    Person save(Person person);

    Person findOneByUsername(String username);

    Iterable<Person> findAll();
}
