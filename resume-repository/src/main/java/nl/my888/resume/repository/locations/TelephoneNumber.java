package nl.my888.resume.repository.locations;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import nl.my888.resume.repository.common.FieldConstants;

@Embeddable
public class TelephoneNumber {

    public enum Type {
        HOME, WORK, MOBILE
    }

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(length = FieldConstants.IDENTIFIER_LENGTH)
    private String number;

    @Column(length = FieldConstants.IDENTIFIER_LENGTH)
    private String areaCode;

}
