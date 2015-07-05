package nl.my888.resume.hal.resources.people;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import nl.my888.resume.hal.constants.ProfileUtil;
import nl.my888.resume.hal.constants.ResumeRelationTypes;
import nl.my888.resume.repository.people.PersonalName;
import nl.my888.springframework.hateoas.links.ProfileLink;
import nl.my888.springframework.hateoas.resource.EmbeddedResourceSupport;
import org.joda.time.LocalDate;
import org.springframework.hateoas.core.Relation;

@Relation(value = ResumeRelationTypes.PERSON, collectionRelation = ResumeRelationTypes.ITEMS)
public class PersonResource extends EmbeddedResourceSupport {

    public static final URI PROFILE_URI = ProfileUtil.toProfileUri("person");

    private final PersonalName name;
    private final LocalDate birthday;

    @JsonCreator
    public PersonResource(@JsonProperty("name") PersonalName name,
            @JsonProperty("birthday") LocalDate birthday) {
        this.name = name;
        this.birthday = birthday;

        add(createProfileLink());
    }

    private ProfileLink createProfileLink() {
        return new ProfileLink(PROFILE_URI);
    }

    public PersonalName getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }
}
