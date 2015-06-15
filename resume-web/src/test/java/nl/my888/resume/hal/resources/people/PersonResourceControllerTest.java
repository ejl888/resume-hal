package nl.my888.resume.hal.resources.people;

import nl.my888.resume.hal.common.MockMvcTest;
import nl.my888.resume.hal.config.HalApiConfig;
import nl.my888.resume.hal.mocks.TestServiceConfig;
import nl.my888.resume.repository.Person;
import nl.my888.resume.services.people.PersonService;
import nl.my888.test.easymock.EchoArgumentAnswer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static nl.my888.springframework.test.web.servlet.halmatchers.RootResourceMatcher.rootResource;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {HalApiConfig.class, TestServiceConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PersonResourceControllerTest extends MockMvcTest {

    @Autowired
    private PersonService mockPersonService;

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

    @Test
    public void testPut() throws Exception {
        final String newPersonContent = "{ \"name\": {\"fullName\": \"M. van der Laan\" } }";

        expect(mockPersonService.savePerson(anyObject(Person.class))).andAnswer(new EchoArgumentAnswer<Person>()).once();
        replay(mockPersonService);

        perform(put("/people/ejl888")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newPersonContent))
                .andExpect(rootResource()
                                .havingSelfLink("/people/ejl888")
                                .havingProfile(PersonResource.PROFILE_URI)
                                .nestedObject("name")
                                .havingProperty("fullName", equalTo("M. van der Laan"))
                );
    }

}