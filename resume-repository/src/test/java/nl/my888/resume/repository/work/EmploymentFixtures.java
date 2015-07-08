package nl.my888.resume.repository.work;

import nl.my888.resume.repository.organizations.Organization;
import nl.my888.resume.repository.people.Person;
import nl.my888.test.easymock.idgenerators.IdGenerators;

/**
 * Created by ejl on 05/07/15.
 */
public class EmploymentFixtures {


    public static Employment persistedEmployment(Employment employment) {
        employment.setId(IdGenerators.longIdGenerator().newId());
        return employment;
    }

    public static Employment createValidJob(Person employee, Organization employer) {
        final Employment result = new Employment(employee, employer);
        return result;
    }
}
