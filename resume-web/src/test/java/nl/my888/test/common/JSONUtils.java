package nl.my888.test.common;

import static java.lang.String.format;

/**
 * Created by ejl on 08/07/15.
 */
public final class JSONUtils {

    /**
     * Convert Java friendly Json format (' instead of ") to real JSON.
     *
     * @param javaFriendlyJson
     * @return
     */
    public static String fromJavaFriendlyJson(String javaFriendlyJson, Object... args) {
        return format(javaFriendlyJson.replaceAll("\'", "\""), args);
    }
}
