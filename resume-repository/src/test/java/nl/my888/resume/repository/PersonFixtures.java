package nl.my888.resume.repository;

import nl.my888.resume.repository.people.Person;
import nl.my888.resume.repository.people.PersonalName;

public class PersonFixtures {

    private PersonFixtures() {

    }

    public static Person createValidPerson(String username) {
        final Person result = new Person(username);

        final PersonalName personalName = new PersonalName();
        personalName.setFullName("Mr. T van 't Tester");
        personalName.setGivenName("Ted");
        personalName.setSurname("Tester");
        personalName.setSurnamePrefix("van 't");

        result.setName(personalName);

        return result;
    }

}
