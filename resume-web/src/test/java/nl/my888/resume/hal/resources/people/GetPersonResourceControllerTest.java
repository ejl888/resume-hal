package nl.my888.resume.hal.resources.people;

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
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {HalApiConfig.class, TestServiceConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GetPersonResourceControllerTest extends MockMvcTest {

    @Test
    public void testGet() throws Exception {
        perform(get("/people/ejl888"))
                .andExpect(rootResource()
                        .havingSelfLink("/people/ejl888")
                        .havingProfile(PersonResource.PROFILE_URI)
                        .nestedObject("name")
                        .havingProperty("fullName", equalTo("E. van der Laan"))
                );

    }
}
