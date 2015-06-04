package nl.my888.resume.hal.constants;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * 888ict resume HAL profile util.
 */
public final class ProfileUtil {

    private static final String PROFILE_BASE = "http://www.888ict.nl/profiles/resume/";

    private ProfileUtil() {
        // const class.
    }

    /**
     * Converteer resourcenaam naar profile-URI. 
     * @param resourceName de resource naam.
     * @return de profile-URI.
     */
    public static URI toProfileUri(String resourceName) {
        try {
            return new URI(ProfileUtil.PROFILE_BASE + resourceName);
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
    
}
