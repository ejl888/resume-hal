package nl.my888.resume.repository.people;

import org.springframework.data.repository.CrudRepository;

public interface PeopleRepository extends CrudRepository<Person, Long> {

    Person findByUsername(String username);
}
