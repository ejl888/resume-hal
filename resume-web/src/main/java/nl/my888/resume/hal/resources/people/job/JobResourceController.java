package nl.my888.resume.hal.resources.people.job;

import nl.my888.resume.repository.work.Employment;
import nl.my888.resume.services.work.EmploymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
@RequestMapping("/people")
public class JobResourceController {

    private static final String JOB_ID_TEMPLATE = "/{personId}/jobs/{organizationId}";

    @Autowired
    private EmploymentService employmentService;

    @Autowired
    private JobResourceAssembler jobResourceAssembler;


    @RequestMapping(value = JOB_ID_TEMPLATE, method = RequestMethod.PUT)
    public ResponseEntity<JobResource> update(
            @PathVariable("personId") Long personId,
            @PathVariable("organizationId") Long organizationId) {

        final Employment job = employmentService.findOrCreateEmployment(personId, organizationId);

        final Employment savedJob = employmentService.save(job);

        return new ResponseEntity<>(jobResourceAssembler.toResource(savedJob), HttpStatus.OK);
    }

    @RequestMapping(value = JOB_ID_TEMPLATE, method = RequestMethod.GET)
    public ResponseEntity<JobResource> read(
            @PathVariable("personId") Long personId,
            @PathVariable("organizationId") Long organizationId) {

        final Employment job = employmentService.getEmployment(personId, organizationId);

        return new ResponseEntity<>(jobResourceAssembler.toResource(job), HttpStatus.OK);
    }

    @RequestMapping(value = "/{personId}/jobs", method = RequestMethod.GET)
    public ResponseEntity<Resources<JobResource>> jobs(
            @PathVariable("personId") Long personId) {

        final Iterable<Employment> jobs = employmentService.findEmploymentsByPerson(personId);
        return new ResponseEntity<>(
                jobResourceAssembler.toResourcesForMethod(methodOn(getClass()).jobs(personId), jobs),
                HttpStatus.OK);
    }
}
