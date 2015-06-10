package nl.my888.resume.repository;

import javax.persistence.Embeddable;

/**
 * Created by ejl on 31/05/15.
 */
@Embeddable
public class PersonalName {

    private String fullName;

    private String givenName;

    private String surname;

    private String surnamePrefix;


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurnamePrefix() {
        return surnamePrefix;
    }

    public void setSurnamePrefix(String surnamePrefix) {
        this.surnamePrefix = surnamePrefix;
    }
}
