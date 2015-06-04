package nl.my888.resume.hal.resources.root;

import java.net.URI;

import nl.my888.resume.hal.constants.ProfileUtil;
import nl.my888.springframework.hateoas.resource.EmbeddedResourceSupport;

public class RootResource extends EmbeddedResourceSupport {

    public static final URI PROFILE_URI = ProfileUtil.toProfileUri("root");

    private final String version = "0.1.0";

    public String getVersion() {
        return version;
    }
}
