package nl.my888.resume.hal.resources.people.jobs;

import nl.my888.resume.hal.resources.employments.EmploymentResource;
import nl.my888.resume.hal.resources.employments.EmploymentResourceAssembler;
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
public class JobsResourceController {

    @Autowired
    private EmploymentService employmentService;

    @Autowired
    private EmploymentResourceAssembler employmentResourceAssembler;

    @RequestMapping(value = "/{personId}/jobs", method = RequestMethod.GET)
    public ResponseEntity<Resources<EmploymentResource>> jobs(@PathVariable("personId") Long personId) {

        final Iterable<Employment> jobs = employmentService.findEmploymentsByPerson(personId);
        final Resources<EmploymentResource> employmentResources = employmentResourceAssembler.toResourcesForMethod(
                methodOn(getClass()).jobs(personId), jobs);

        // TODO add link to add employer?

        return new ResponseEntity<>(employmentResources, HttpStatus.OK);
    }
}
