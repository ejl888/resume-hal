package nl.my888.resume.hal.constants;

import nl.my888.resume.hal.config.HalApiConfig;

public final class ResumeRelationTypes {

    public static final String PERSON = "person";
    public static final String ITEMS = "items";


    public static String asCurriedRelation(String type) {
        return HalApiConfig.RELATION_PREFIX + ":" + type;
    }

    private ResumeRelationTypes() {
        // constants class
    }
}
