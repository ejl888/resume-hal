package nl.my888.resume.hal.resources.employments;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonCreator;
import nl.my888.resume.hal.constants.ProfileUtil;
import nl.my888.resume.hal.constants.ResumeRelationTypes;
import nl.my888.springframework.hateoas.links.ProfileLink;
import nl.my888.springframework.hateoas.resource.EmbeddedResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(value = ResumeRelationTypes.EMPLOYMENT, collectionRelation = ResumeRelationTypes.ITEMS)
public class EmploymentResource extends EmbeddedResourceSupport {

    public static final URI PROFILE_URI = ProfileUtil.toProfileUri("employments");

    @JsonCreator
    public EmploymentResource() {
        add(createProfileLink());
    }

    private ProfileLink createProfileLink() {
        return new ProfileLink(PROFILE_URI);
    }

}
