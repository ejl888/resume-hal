package nl.my888.resume.services.people.impl;

import javax.persistence.EntityNotFoundException;

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
    public Person save(Person person) {
        return peopleRepository.save(person);
    }

    @Override
    public Person findOneByUsername(String username) {
        return peopleRepository.findByUsername(username);
    }

    @Override
    public Iterable<Person> findAll() {
        return peopleRepository.findAll();
    }

    @Override
    public Person findOne(Long id) {
        return peopleRepository.findOne(id);
    }

    @Override
    public Person getPerson(Long id) {
        final Person result = findOne(id);
        if (result == null) {
            throw new EntityNotFoundException(String.format("Person %s not found!", id));
        }
        return result;
    }

    @Override
    public Person getPersonByUsername(String username) {
        final Person result = findOneByUsername(username);
        if (result == null) {
            throw new EntityNotFoundException(String.format("User %s not found!", username));
        }
        return result;
    }
}
