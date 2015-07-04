package nl.my888.resume.repository.work;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import nl.my888.resume.repository.common.DateRange;
import nl.my888.resume.repository.organizations.Organization;
import nl.my888.resume.repository.people.Person;

/**
 * Employement relation.
 */
@Entity
public class Employment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Person employee;

    @ManyToOne
    private Organization employer;

    @Embedded
    private DateRange period;

    public Employment(Person employee, Organization employer) {
        this.employee = employee;
        this.employer = employer;
    }

    protected Employment() {
        // JPA only!
    }

    public DateRange getPeriod() {
        return period;
    }

    public Organization getEmployer() {
        return employer;
    }

    public Person getEmployee() {
        return employee;
    }

    public Long getId() {
        return id;
    }
}
