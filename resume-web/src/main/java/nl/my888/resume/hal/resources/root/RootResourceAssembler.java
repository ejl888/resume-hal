package nl.my888.resume.hal.resources.root;

import nl.my888.springframework.hateoas.links.ExtendedLinkBuilder;
import nl.my888.springframework.hateoas.links.ProfileLink;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;

@Component
public class RootResourceAssembler extends ResourceAssemblerSupport<Void, RootResource> {

    public RootResourceAssembler() {
        super(RootResourceController.class, RootResource.class);
    }

    public RootResource toResource() {
        final RootResource result = new RootResource();

        result.add(createSelfLink());
        result.add(createCurrentUserLink());

        return result;
    }

    private Link createCurrentUserLink() {
        return ExtendedLinkBuilder.linkTo(methodOn(RootResourceController.class).getCurrentUser()).withRel("888ict:current-user");
    }

    private Link createProfileLink() {
        return new ProfileLink(RootResource.PROFILE_URI);
    }

    private Link createSelfLink() {
        return ControllerLinkBuilder.linkTo(RootResourceController.class).withSelfRel();
    }

    @Override
    public RootResource toResource(Void entity) {
        return toResource();
    }
}
