package nl.my888.resume.hal.resources.root;

import nl.my888.resume.hal.common.MockMvcTest;
import nl.my888.resume.hal.config.HalApiConfig;
import nl.my888.resume.hal.mocks.TestServiceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static nl.my888.springframework.test.web.servlet.halmatchers.RootResourceMatcher.rootResource;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {HalApiConfig.class, TestServiceConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GetRootResourceControllerTest extends MockMvcTest {

    @Test
    public void testGetRoot() throws Exception {
        perform(get("/root"))
                .andExpect(jsonPath("_links['profile'].href").value(endsWith("root")))
                .andExpect(rootResource()
                                .havingProperty("version", equalTo("0.1.0"))
                                .havingSelfLink("/root")
                                .havingProfile(RootResource.PROFILE_URI)
                );
    }
}
