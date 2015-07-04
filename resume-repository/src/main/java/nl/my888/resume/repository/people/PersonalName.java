package nl.my888.resume.repository.people;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import nl.my888.resume.repository.common.FieldConstants;

/**
 * Created by ejl on 31/05/15.
 */
@Embeddable
public class PersonalName {

    @Column(length = FieldConstants.LABEL_LENGTH)
    private String fullName;

    @Column(length = FieldConstants.LABEL_LENGTH)
    private String givenName;

    @Column(length = FieldConstants.LABEL_LENGTH)
    private String surname;

    @Column(length = FieldConstants.LABEL_LENGTH)
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
