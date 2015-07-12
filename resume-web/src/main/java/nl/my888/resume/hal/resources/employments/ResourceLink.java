package nl.my888.resume.hal.resources.employments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ejl on 11/07/15.
 */
public class ResourceLink {

    private final String href;

    @JsonCreator
    public ResourceLink(@JsonProperty("href") String href) {
        this.href = href;
    }

    public String getHref() {
        return href;
    }

}
