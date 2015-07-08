package nl.my888.resume.hal.resources.employments;

import nl.my888.resume.hal.common.MockMvcTest;
import nl.my888.resume.hal.config.HalApiConfig;
import nl.my888.resume.hal.constants.ResumeRelationTypes;
import nl.my888.resume.hal.mocks.TestServiceConfig;
import nl.my888.resume.repository.organizations.Organization;
import nl.my888.resume.repository.people.Person;
import nl.my888.resume.repository.work.Employment;
import nl.my888.resume.services.work.EmploymentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static nl.my888.resume.repository.organizations.OrganizationFixtures.createValidOrganization;
import static nl.my888.resume.repository.people.PersonFixtures.createValidUser;
import static nl.my888.resume.repository.people.PersonFixtures.persistedPerson;
import static nl.my888.resume.repository.work.EmploymentFixtures.createValidJob;
import static nl.my888.resume.repository.work.EmploymentFixtures.persistedEmployment;
import static nl.my888.springframework.test.web.servlet.halmatchers.RootResourceMatcher.rootResource;
import static nl.my888.test.easymock.idgenerators.IdGenerators.longIdGenerator;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {HalApiConfig.class, TestServiceConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EmploymentResourceControllerTest extends MockMvcTest {

    @Autowired
    private EmploymentService employmentService;



    @Test
    public void testGet() throws Exception {

        Person person = persistedPerson(createValidUser("testit"));
        Organization organization1 = createValidOrganization(longIdGenerator().newId());
        Employment givenEmployment = persistedEmployment(createValidJob(person, organization1));

        expect(employmentService.getEmployment(givenEmployment.getId()))
                .andReturn(givenEmployment)
                .once();
        replay(employmentService);

        perform(get("/employments/{id}", givenEmployment.getId()))
                .andExpect(rootResource()
                                .havingSelfLink("/employments/{id}", givenEmployment.getId())
                                .havingProfile(EmploymentResource.PROFILE_URI)
                                .linkedResource(ResumeRelationTypes.asCurriedRelation(ResumeRelationTypes.EMPLOYEE))
                                .havingLinkTo("/people/{personId}", person.getId())
                                .parentResource()
                                .linkedResource(ResumeRelationTypes.asCurriedRelation(ResumeRelationTypes.EMPLOYER))
                                .havingLinkTo("/organizations/{organizationId}", organization1.getId())
                );
    }

}
