package nl.my888.resume.hal.resources.root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/root")
public class RootResourceController {

    @Autowired
    private RootResourceAssembler rootResourceAssembler;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<RootResource> get() {
        return new ResponseEntity<>(rootResourceAssembler.toResource(), HttpStatus.OK);
    }
}
