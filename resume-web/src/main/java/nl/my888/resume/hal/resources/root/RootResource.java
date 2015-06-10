package nl.my888.resume.hal.resources.root;

import java.net.URI;

import nl.my888.resume.hal.constants.ProfileUtil;
import nl.my888.springframework.hateoas.links.ProfileLink;
import nl.my888.springframework.hateoas.resource.EmbeddedResourceSupport;
import org.springframework.hateoas.Link;

public class RootResource extends EmbeddedResourceSupport {

    public static final URI PROFILE_URI = ProfileUtil.toProfileUri("root");

    private final String version = "0.1.0";

    public String getVersion() {
        return version;
    }

    public RootResource() {
        add(createProfileLink());
    }

    private Link createProfileLink() {
        return new ProfileLink(PROFILE_URI);
    }
}
