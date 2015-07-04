package nl.my888.resume.hal.resources.organization;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import nl.my888.resume.hal.constants.ProfileUtil;
import nl.my888.resume.hal.constants.ResumeRelationTypes;
import nl.my888.springframework.hateoas.links.ProfileLink;
import nl.my888.springframework.hateoas.resource.EmbeddedResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(value = ResumeRelationTypes.ORGANIZATION, collectionRelation = ResumeRelationTypes.ITEMS)
public class OrganizationResource extends EmbeddedResourceSupport {

    public static final URI PROFILE_URI = ProfileUtil.toProfileUri("organization");

    private final String name;

    @JsonCreator
    public OrganizationResource(@JsonProperty("name") String name) {
        this.name = name;

        add(createProfileLink());
    }

    private ProfileLink createProfileLink() {
        return new ProfileLink(PROFILE_URI);
    }

    public String getName() {
        return name;
    }
}
