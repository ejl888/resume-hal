package nl.my888.resume.hal.resources.employments;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.my888.resume.repository.organizations.Organization;
import nl.my888.resume.repository.people.Person;
import nl.my888.resume.repository.work.Employment;
import nl.my888.resume.services.organization.OrganizationService;
import nl.my888.resume.services.people.PersonService;
import nl.my888.resume.services.work.EmploymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/employments")
public class EmploymentResourceController {

    @Autowired
    private EmploymentService employmentService;

    @Autowired
    private PersonService personService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private EmploymentResourceAssembler employmentResourceAssembler;


    private static final Pattern PERSON_PATTERN = Pattern.compile(".*/people/([0-9]+)");
    private static final Pattern ORGANIZATION_PATTERN = Pattern.compile(".*/organizations/([0-9]+)");


    @RequestMapping(method = RequestMethod.POST, consumes = "application/vnd.nl.888ict.employment+json")
    public ResponseEntity<EmploymentResource> create(
            @RequestBody CreateEmploymentResource employmentResource) {
        // TODO
        // bepaal de employer obv (relatieve) href
        // bepaal de employee ob link

        final Person employee = personService.getPerson(
                extractEmployeeId(employmentResource.getEmployee().getHref()));
        final Organization employer = organizationService.getOrganization(
                extractEmployerId(employmentResource.getEmployer().getHref()));
        final Employment job = new Employment(employee, employer);

        final Employment savedJob = employmentService.save(job);

        return new ResponseEntity<>(employmentResourceAssembler.toResource(savedJob), HttpStatus.CREATED);
    }

    private Long extractEmployerId(String href) {
        final Matcher matcher = ORGANIZATION_PATTERN.matcher(href);
        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }
        return null;
    }

    private Long extractEmployeeId(String href) {
        final Matcher matcher = PERSON_PATTERN.matcher(href);
        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }
        return null;
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<EmploymentResource> update(@PathVariable("id") Long id) {

        final Employment job = employmentService.getEmployment(id);

        final Employment savedJob = employmentService.save(job);

        return new ResponseEntity<>(employmentResourceAssembler.toResource(savedJob), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<EmploymentResource> read(@PathVariable("id") Long id) {

        final Employment job = employmentService.getEmployment(id);

        return new ResponseEntity<>(employmentResourceAssembler.toResource(job), HttpStatus.OK);
    }
}
