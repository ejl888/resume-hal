package nl.my888.resume.services.people.impl;

import nl.my888.resume.repository.people.PeopleRepository;
import nl.my888.resume.repository.people.Person;
import nl.my888.resume.services.people.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PeopleRepository peopleRepository;

    @Override
    public Person savePerson(Person person) {
        return peopleRepository.save(person);
    }

    @Override
    public Person getPersonByUsername(String username) {
        return peopleRepository.findByUsername(username);
    }

    @Override
    public Iterable<Person> findAll() {
        return peopleRepository.findAll();
    }
}
