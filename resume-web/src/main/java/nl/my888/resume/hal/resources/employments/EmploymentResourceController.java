package nl.my888.resume.hal.resources.employments;

import nl.my888.resume.repository.work.Employment;
import nl.my888.resume.services.work.EmploymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/employments")
public class EmploymentResourceController {

    @Autowired
    private EmploymentService employmentService;

    @Autowired
    private EmploymentResourceAssembler employmentResourceAssembler;

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
