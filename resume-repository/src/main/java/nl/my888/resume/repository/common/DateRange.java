package nl.my888.resume.repository.common;

import javax.persistence.Embeddable;

import org.joda.time.LocalDate;

/**
 * Date range (without time).
 */
@Embeddable
public class DateRange {

    private LocalDate startDate;

    private LocalDate endDate;


}
