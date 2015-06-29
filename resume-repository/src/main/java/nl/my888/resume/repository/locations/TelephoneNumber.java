package nl.my888.resume.repository.locations;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class TelephoneNumber {

    public enum Type {
        HOME, WORK, MOBILE
    }

    @Enumerated(EnumType.STRING)
    private Type type;

    @Basic
    private String number;

    @Basic
    private String areaCode;

}
