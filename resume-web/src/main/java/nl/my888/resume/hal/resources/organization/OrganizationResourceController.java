package nl.my888.resume.hal.resources.organization;

import nl.my888.resume.repository.organizations.Organization;
import nl.my888.resume.services.organization.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
@RequestMapping("/organizations")
public class OrganizationResourceController {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationResourceAssembler organizationResourceAssembler;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Resources<OrganizationResource>> find() {
        final Iterable<Organization> users = organizationService.findAll();

        return new ResponseEntity<>(
                organizationResourceAssembler.toResourcesForMethod(methodOn(getClass()).find(), users),
                HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<OrganizationResource> create(@RequestBody OrganizationResource organizationResource) {
        final Organization organization = new Organization();

        // TODO merge resource
        organization.setName(organizationResource.getName());

        final Organization savedOrganization = organizationService.save(organization);

        return new ResponseEntity<>(organizationResourceAssembler.toResource(savedOrganization), HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<OrganizationResource> read(@PathVariable("id") Long id) {
        final Organization organization = organizationService.getOrganization(id);

        return new ResponseEntity<>(organizationResourceAssembler.toResource(organization), HttpStatus.OK);
    }

    /**
     * PUT, because the request is idempotent.
     * @param id
     * @param organizationResource
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrganizationResource> update(
            @PathVariable("id") Long id,
            @RequestBody OrganizationResource organizationResource) {

        final Organization organization = organizationService.getOrganization(id);

        // TODO merge resource
        organization.setName(organizationResource.getName());

        final Organization savedOrganization = organizationService.save(organization);

        return new ResponseEntity<>(organizationResourceAssembler.toResource(savedOrganization), HttpStatus.OK);
    }

}
