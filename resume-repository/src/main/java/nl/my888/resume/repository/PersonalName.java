package nl.my888.resume.repository;

import javax.persistence.Embeddable;

/**
 * Created by ejl on 31/05/15.
 */
@Embeddable
public class PersonalName {

    private String givenName;

    private String surname;

    private String surnamePrefix;

}
