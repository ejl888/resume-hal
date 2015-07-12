package nl.my888.resume.repository.work;

import javax.persistence.Embedded;

import nl.my888.resume.repository.common.DateRange;

/**
 * Employment properties, without any relations.
 */
public class EmploymentProperties {

    @Embedded
    private DateRange period;

    public DateRange getPeriod() {
        return period;
    }
}
