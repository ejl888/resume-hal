package nl.my888.resume.repository.locations;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import nl.my888.resume.repository.common.FieldConstants;

/**
 * Address modeled after hCard.
 */
@Embeddable
public class Address {

    public enum Type {
        WORK, HOME, PREF, POSTAL
    }

    @Column(length = FieldConstants.LABEL_LENGTH)
    private String postOfficeBox;

    @Column(length = FieldConstants.LABEL_LENGTH)
    private String streetAddress;

    @Column(length = FieldConstants.LABEL_LENGTH)
    private String extendedAddress;

    @Column(length = FieldConstants.IDENTIFIER_LENGTH)
    private String postalCode;

    // i.e. city
    @Column(length = FieldConstants.LABEL_LENGTH)
    private String locality;

    @Column(length = FieldConstants.LABEL_LENGTH)
    private String countryName;

    // i.e. state
    @Column(length = FieldConstants.LABEL_LENGTH)
    private String region;

    @Enumerated(EnumType.STRING)
    private Type type;

    public String getPostOfficeBox() {
        return postOfficeBox;
    }

    public void setPostOfficeBox(String postOfficeBox) {
        this.postOfficeBox = postOfficeBox;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getExtendedAddress() {
        return extendedAddress;
    }

    public void setExtendedAddress(String extendedAddress) {
        this.extendedAddress = extendedAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
