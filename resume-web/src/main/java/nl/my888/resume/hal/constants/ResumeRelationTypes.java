package nl.my888.resume.hal.constants;

import nl.my888.resume.hal.config.HalApiConfig;

public final class ResumeRelationTypes {

    public static final String ITEMS = "items";

    public static final String PERSON = "person";
    public static final String ORGANIZATION = "organizations";
    public static final String JOB = "jobs";

    public static final String JOBS = "jobs";
    public static final String EMPLOYMENT = "emploment";
    public static final String EMPLOYEE = "employee";
    public static final String EMPLOYER = "employer";


    public static String asCurriedRelation(String type) {
        return HalApiConfig.RELATION_PREFIX + ":" + type;
    }

    private ResumeRelationTypes() {
        // constants class
    }
}
