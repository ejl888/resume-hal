package nl.my888.resume.repository.work;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import nl.my888.resume.repository.common.FieldConstants;

/**
 * Skill. Can be anything one accuires. Java, UML, accounting, etc.
 */
@Entity
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = FieldConstants.LABEL_LENGTH)
    private String name;

    // group, Java 6 => Java => Programming Language
    @ManyToOne
    private Skill group;
}
