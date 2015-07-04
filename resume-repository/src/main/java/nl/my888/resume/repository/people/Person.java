package nl.my888.resume.repository.people;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import nl.my888.resume.repository.common.FieldConstants;
import org.joda.time.LocalDate;

/**
 * Created by ejl on 31/05/15.
 */
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private PersonalName name;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Nationality nationality;

    @Column(length = FieldConstants.IDENTIFIER_LENGTH)
    private String username;


    protected Person() {
        // JPA only!
    }

    public Long getId() {
        return id;
    }

    public Person(String username) {
        this.username = username;
    }

    public PersonalName getName() {
        return name;
    }

    public void setName(PersonalName name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
