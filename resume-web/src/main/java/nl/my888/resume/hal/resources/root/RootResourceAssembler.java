package nl.my888.resume.hal.resources.root;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RootResourceAssembler extends ResourceAssemblerSupport<Void, RootResource> {

    public RootResourceAssembler() {
        super(RootResourceController.class, RootResource.class);
    }

    public RootResource toResource() {
        return new RootResource();
    }

    @Override
    public RootResource toResource(Void entity) {
        return toResource();
    }
}
