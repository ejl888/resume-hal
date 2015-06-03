package nl.my888.resume.hal.resources.root;

import org.springframework.hateoas.ResourceSupport;

public class RootResource extends ResourceSupport {

    private final String version = "0.1.0";

    public String getVersion() {
        return version;
    }
}
