package nl.my888.resume.repository;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.joda.time.LocalDate;

/**
 * Created by ejl on 31/05/15.
 */
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Embedded
    private PersonalName name;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Nationality nationality;

}
