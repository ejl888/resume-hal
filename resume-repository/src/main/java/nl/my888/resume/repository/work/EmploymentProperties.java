package nl.my888.resume.repository.work;

import javax.persistence.Column;
import javax.persistence.Embedded;

import nl.my888.resume.repository.common.DateRange;
import nl.my888.resume.repository.common.FieldConstants;

/**
 * Employment properties, without any relations.
 */
public class EmploymentProperties {

    @Column(length = FieldConstants.LABEL_LENGTH)
    private String title;

    @Embedded
    private DateRange period;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DateRange getPeriod() {
        return period;
    }

    public void setPeriod(DateRange period) {
        this.period = period;
    }

}
