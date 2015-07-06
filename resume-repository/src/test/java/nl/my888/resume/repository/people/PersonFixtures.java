package nl.my888.resume.repository.people;

import nl.my888.test.easymock.idgenerators.IdGenerators;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public final class PersonFixtures {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("dd/MM/yyyy");
    private static final LocalDate BIRTHDAY_VALID_PERSON = LocalDate.parse("24/09/1970", DATE_TIME_FORMATTER);

    private PersonFixtures() {
        // util
    }

    public static Person persisted(Person person) {
        person.setId(IdGenerators.longIdGenerator().newId());
        return person;
    }

    public static Person createValidPerson() {
        final Person result = new Person();

        return setFields(result);
    }

    public static Person createValidUser(String username) {
        final Person result = new Person(username);

        return setFields(result);
    }

    private static Person setFields(Person result) {
        final PersonalName personalName = new PersonalName();
        personalName.setFullName("Mr. T van 't Tester");
        personalName.setGivenName("Ted");
        personalName.setSurname("Tester");
        personalName.setSurnamePrefix("van 't");

        result.setName(personalName);

        result.setBirthday(BIRTHDAY_VALID_PERSON);

        return result;
    }


}
