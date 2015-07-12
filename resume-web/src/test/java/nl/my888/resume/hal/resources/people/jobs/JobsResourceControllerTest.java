package nl.my888.resume.hal.resources.people.jobs;

import java.util.Arrays;

import nl.my888.resume.hal.common.MockMvcTest;
import nl.my888.resume.hal.config.HalApiConfig;
import nl.my888.resume.hal.constants.ResumeRelationTypes;
import nl.my888.resume.hal.mocks.TestServiceConfig;
import nl.my888.resume.hal.resources.employments.EmploymentResource;
import nl.my888.resume.repository.organizations.Organization;
import nl.my888.resume.repository.people.Person;
import nl.my888.resume.repository.work.Employment;
import nl.my888.resume.repository.work.EmploymentFixtures;
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
import static nl.my888.springframework.test.web.servlet.halmatchers.RootResourceMatcher.rootResource;
import static nl.my888.test.easymock.idgenerators.IdGenerators.longIdGenerator;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {HalApiConfig.class, TestServiceConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JobsResourceControllerTest extends MockMvcTest {



    @Autowired
    private EmploymentService employmentService;

    @Test
    public void testGetPersonJobs() throws Exception {
        Person person = persistedPerson(createValidUser("testit"));

        Organization organization1 = createValidOrganization(longIdGenerator().newId());
        Organization organization2 = createValidOrganization(longIdGenerator().newId());

        Employment job1 = EmploymentFixtures.persistedEmployment(createValidJob(person, organization1));
        Employment job2 = EmploymentFixtures.persistedEmployment(createValidJob(person, organization2));

        expect(employmentService.findEmploymentsByPerson(person.getId()))
                .andReturn(Arrays.asList(job1, job2))
                .once();
        replay(employmentService);

        perform(get("/people/{userId}/jobs", person.getId()))
                .andExpect(rootResource()
                                .havingSelfLink("/people/{id}/jobs", person.getId())
                                .havingProfile(HalApiConfig.COLLECTION_PROFILE)
                                .embeddedResource(ResumeRelationTypes.asCurriedRelation(ResumeRelationTypes.ITEMS))
                                .havingSize(2)
                                .arrayItem(0)
                                .havingProfile(EmploymentResource.PROFILE_URI)
                                .havingSelfLink("/employments/{id}", job1.getId())
                );
    }

}
