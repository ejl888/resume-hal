package nl.my888.resume.hal.resources.employment;

import javax.persistence.EntityNotFoundException;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/persons")
public class JobResourceController {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private PersonService personService;

    @Autowired
    private EmploymentService employmentService;

    @Autowired
    private JobResourceAssembler jobResourceAssembler;


    @RequestMapping(value = "/{personId}/employers/{organizationId}", method = RequestMethod.PUT)
    public ResponseEntity<JobResource> update(
            @PathVariable("personId") Long personId,
            @PathVariable("organizationId") Long organizationId) {

        final Organization organization = organizationService.getOrganization(organizationId);
        final Person person = personService.getPerson(personId);

        final Employment job = new Employment(person, organization);

        final Employment savedJob = employmentService.save(job);

        return new ResponseEntity<>(jobResourceAssembler.toResource(savedJob), HttpStatus.OK);
    }

    @RequestMapping(value = "/{personId}/employers/{organizationId}", method = RequestMethod.GET)
    public ResponseEntity<JobResource> read(
            @PathVariable("personId") Long personId,
            @PathVariable("organizationId") Long organizationId) {

        final Employment job = employmentService.getEmployment(personId, organizationId);

        return new ResponseEntity<>(jobResourceAssembler.toResource(job), HttpStatus.OK);
    }

}
