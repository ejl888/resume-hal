package nl.my888.resume.hal.resources.organization;

import java.util.Arrays;

import nl.my888.resume.hal.common.MockMvcTest;
import nl.my888.resume.hal.config.HalApiConfig;
import nl.my888.resume.hal.constants.ResumeRelationTypes;
import nl.my888.resume.hal.mocks.TestServiceConfig;
import nl.my888.resume.repository.organizations.Organization;
import nl.my888.resume.services.organization.OrganizationService;
import nl.my888.test.easymock.EchoArgumentAnswer;
import nl.my888.test.easymock.PersistedEchoArgumentAnswer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static nl.my888.resume.hal.constants.ResumeRelationTypes.asCurriedRelation;
import static nl.my888.resume.repository.organizations.OrganizationFixtures.createValidOrganization;
import static nl.my888.springframework.test.web.servlet.halmatchers.RootResourceMatcher.rootResource;
import static nl.my888.test.easymock.idgenerators.IdGenerators.longIdGenerator;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {HalApiConfig.class, TestServiceConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrganizationResourceControllerTest extends MockMvcTest {

    @Autowired
    private OrganizationService mockOrganizationService;

    @Test
    public void testGetAll() throws Exception {

        Organization givenOrganization1 = createValidOrganization(1L);
        Organization givenOrganization2 = createValidOrganization(2L);

        expect(mockOrganizationService.findAll())
                .andReturn(Arrays.asList(givenOrganization1, givenOrganization2))
                .once();
        replay(mockOrganizationService);

        perform(get("/organizations"))
                .andExpect(rootResource()
                                .havingProfile(HalApiConfig.COLLECTION_PROFILE)
                                .embeddedResource(asCurriedRelation(ResumeRelationTypes.ITEMS))
                                .havingSize(2)
                );
    }


    @Test
    public void testGet() throws Exception {

        Organization givenOrganization = createValidOrganization(1L);
        expect(mockOrganizationService.findOne(givenOrganization.getId()))
                .andReturn(givenOrganization).once();
        replay(mockOrganizationService);

        perform(get("/organizations/{id}", givenOrganization.getId()))
                .andExpect(rootResource()
                                .havingSelfLink("/organizations/{id}", givenOrganization.getId())
                                .havingProfile(OrganizationResource.PROFILE_URI)
                                .havingProperty("name", equalTo(givenOrganization.getName()))
                );
    }

    @Test
    public void testPutExistingOrganization() throws Exception {
        final String newPersonContent = "{ \"name\": \"888ict\" }";

        Organization givenOrganization = createValidOrganization(1L);
        expect(mockOrganizationService.findOne(givenOrganization.getId()))
                .andReturn(givenOrganization).once();
        expect(mockOrganizationService.save(anyObject(Organization.class)))
                .andAnswer(new EchoArgumentAnswer<Organization>()).once();
        replay(mockOrganizationService);

        perform(put("/organizations/{id}", givenOrganization.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(newPersonContent))
                .andExpect(rootResource()
                                .havingSelfLink("/organizations/{id}", givenOrganization.getId())
                                .havingProfile(OrganizationResource.PROFILE_URI)
                                .havingProperty("name", equalTo("888ict"))
                );

    }

    @Test
    public void testCreateNeworganization() throws Exception {
        final String newOrganizationContent = "{ \"name\": \"888ict\" }";

        final PersistedEchoArgumentAnswer<Organization, Long> answer = createOrganizationPersistedArgumentAnswer();
        expect(mockOrganizationService.save(anyObject(Organization.class)))
                .andAnswer(answer)
                .once();
        replay(mockOrganizationService);

        perform(post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newOrganizationContent))
                .andExpect(rootResource()
                                .havingSelfLink("/organizations/{id}", answer.answered().getId())
                                .havingProfile(OrganizationResource.PROFILE_URI)
                                .havingProperty("name", equalTo("888ict"))
                );

    }

    private static PersistedEchoArgumentAnswer<Organization, Long> createOrganizationPersistedArgumentAnswer() {
        return new PersistedEchoArgumentAnswer<>(Organization.class, longIdGenerator());
    }

}
