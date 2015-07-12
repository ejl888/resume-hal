package nl.my888.resume.repository.work;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import nl.my888.resume.repository.organizations.Organization;
import nl.my888.resume.repository.people.Person;

/**
 * Employment relation.
 */
@Entity
public class Employment extends EmploymentProperties {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Person employee;

    @ManyToOne
    private Organization employer;

    public Employment(Person employee, Organization employer) {
        this.employee = employee;
        this.employer = employer;
    }

    protected Employment() {
        // JPA only!
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

    void setId(Long id) {
        this.id = id;
    }
}
