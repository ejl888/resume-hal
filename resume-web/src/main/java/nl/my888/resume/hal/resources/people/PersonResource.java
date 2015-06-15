package nl.my888.resume.hal.resources.people;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import nl.my888.resume.hal.constants.ProfileUtil;
import nl.my888.resume.repository.people.PersonalName;
import nl.my888.springframework.hateoas.links.ProfileLink;
import nl.my888.springframework.hateoas.resource.EmbeddedResourceSupport;

public class PersonResource extends EmbeddedResourceSupport {

    public static final URI PROFILE_URI = ProfileUtil.toProfileUri("person");

    private final PersonalName name;

    @JsonCreator
    public PersonResource(@JsonProperty("name") PersonalName name) {
        this.name = name;

        add(createProfileLink());
    }

    private ProfileLink createProfileLink() {
        return new ProfileLink(PROFILE_URI);
    }

    public PersonalName getName() {
        return name;
    }
}
