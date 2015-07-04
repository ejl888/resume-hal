package nl.my888.resume.repository.work;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import nl.my888.resume.repository.common.DateRange;
import nl.my888.resume.repository.common.FieldConstants;
import nl.my888.resume.repository.people.Person;

/**
 * project member relation. Business key = member, project and dateRange.
 */
@Entity
public class ProjectMember {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Person member;

    @ManyToOne
    private Project project;

    @Embedded
    private DateRange dateRange;

    @Column(length = FieldConstants.LABEL_LENGTH)
    private String primaryRole;

    @Column(length = FieldConstants.TEXT_AREA)
    private String responsibilities;

    @Column(length = FieldConstants.TEXT_AREA)
    private String accomplishments;

    @ManyToMany
    private Collection<Skill> skills;
}
