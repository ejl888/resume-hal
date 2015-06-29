package nl.my888.resume.repository.locations;

import javax.persistence.Embeddable;

/**
 * Address modeled after hCard.
 */
@Embeddable
public class Address {

    public enum Type {
        WORK, HOME, PREF, POSTAL
    }

    private String postOfficeBox;

    private String streetAddress;

    private String extendedAddress;

    private String postalCode;

    // i.e. city
    private String locality;

    private String countryName;

    // i.e. state
    private String region;

}
