package nl.my888.resume.hal.resources.people;

import java.util.Arrays;

import nl.my888.resume.hal.common.MockMvcTest;
import nl.my888.resume.hal.config.HalApiConfig;
import nl.my888.resume.hal.constants.ResumeRelationTypes;
import nl.my888.resume.hal.mocks.TestServiceConfig;
import nl.my888.resume.repository.people.Person;
import nl.my888.resume.repository.people.PersonalName;
import nl.my888.resume.services.people.PersonService;
import nl.my888.test.easymock.EchoArgumentAnswer;
import nl.my888.test.easymock.PersistedArgumentAnswer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static nl.my888.resume.hal.constants.ResumeRelationTypes.asCurriedRelation;
import static nl.my888.resume.repository.people.PersonFixtures.createValidPerson;
import static nl.my888.resume.repository.people.PersonFixtures.persisted;
import static nl.my888.springframework.test.web.servlet.halmatchers.RootResourceMatcher.rootResource;
import static nl.my888.test.easymock.idgenerators.IdGenerators.longIdGenerator;
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
    public void testGetAll() throws Exception {

        Person givenPerson1 = persisted(createValidPerson("MrT"));
        Person givenPerson2 = persisted(createValidPerson("MsTrex"));

        expect(mockPersonService.findAll())
                .andReturn(Arrays.asList(givenPerson1, givenPerson2))
                .once();
        replay(mockPersonService);

        perform(get("/people/users"))
                .andExpect(rootResource()
                                .havingProfile(HalApiConfig.COLLECTION_PROFILE)
                                .embeddedResource(asCurriedRelation(ResumeRelationTypes.ITEMS))
                                .havingSize(2)
                );
    }


    @Test
    public void testGet() throws Exception {

        Person givenPerson = persisted(createValidPerson("MrT"));
        expect(mockPersonService.getPersonByUsername(givenPerson.getUsername()))
                .andReturn(givenPerson)
                .once();
        replay(mockPersonService);

        final PersonalName givenPersonName = givenPerson.getName();
        perform(get("/people/users/{username}", givenPerson.getUsername()))
                .andExpect(rootResource()
                                .havingSelfLink("/people/users/{id}", givenPerson.getUsername())
                                .havingProfile(PersonResource.PROFILE_URI)
                                .nestedObject("name")
                                .havingProperty("fullName", equalTo(givenPersonName.getFullName()))
                                .havingProperty("givenName", equalTo(givenPersonName.getGivenName()))
                                .havingProperty("surname", equalTo(givenPersonName.getSurname()))
                                .havingProperty("surnamePrefix", equalTo(givenPersonName.getSurnamePrefix()))
                );
//        verify(mockPersonService);
    }

    @Test
    public void testGetRelations() throws Exception {

        Person givenPerson = persisted(createValidPerson("MrT"));
        expect(mockPersonService.getPersonByUsername(givenPerson.getUsername()))
                .andReturn(givenPerson)
                .once();
        replay(mockPersonService);

        perform(get("/people/users/{username}", givenPerson.getUsername()))
                .andExpect(rootResource()
                                .linkedResource(ResumeRelationTypes.asCurriedRelation(ResumeRelationTypes.JOBS)).exists()
                );
    }

    @Test
    public void testPutExistingUser() throws Exception {
        final String newPersonContent = "{ \"name\": {\"fullName\": \"M. van der Laan\" } }";

        Person givenPerson = persisted(createValidPerson("ejl888"));
        expect(mockPersonService.findOneByUsername(givenPerson.getUsername()))
                .andReturn(givenPerson)
                .once();
        expect(mockPersonService.save(anyObject(Person.class)))
                .andAnswer(new EchoArgumentAnswer<Person>())
                .once();
        replay(mockPersonService);

        perform(put("/people/users/ejl888")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newPersonContent))
                .andExpect(rootResource()
                                .havingSelfLink("/people/users/ejl888")
                                .havingProfile(PersonResource.PROFILE_URI)
                                .nestedObject("name")
                                .havingProperty("fullName", equalTo("M. van der Laan"))
                );

    }

    @Test
    public void testPutNewUser() throws Exception {
        final String newPersonContent = "{ \"name\": {\"fullName\": \"M. van der Laan\" } }";

        expect(mockPersonService.findOneByUsername(anyObject(String.class)))
                .andReturn(null)
                .once();
        expect(mockPersonService.save(anyObject(Person.class)))
                .andAnswer(new PersistedArgumentAnswer<>(Person.class, longIdGenerator()))
                .once();
        replay(mockPersonService);

        perform(put("/people/users/ejl888")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newPersonContent))
                .andExpect(rootResource()
                                .havingSelfLink("/people/users/ejl888")
                                .havingProfile(PersonResource.PROFILE_URI)
                                .nestedObject("name")
                                .havingProperty("fullName", equalTo("M. van der Laan"))
                );

    }

}
