package nl.my888.resume.hal.resources.employments;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import nl.my888.resume.repository.work.EmploymentProperties;

/**
 * Created by ejl on 11/07/15.
 */
public class CreateEmploymentResource extends EmploymentProperties {

    private final ResourceLink employee;
    private final ResourceLink employer;

    @JsonCreator
    public CreateEmploymentResource(
            @JsonProperty("employee") ResourceLink employee,
            @JsonProperty("employer") ResourceLink employer) {
        this.employee = employee;
        this.employer = employer;
    }

    public ResourceLink getEmployee() {
        return employee;
    }

    public ResourceLink getEmployer() {
        return employer;
    }
}
