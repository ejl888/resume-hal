package nl.my888.resume.hal.mocks;

import java.util.HashMap;

import nl.my888.resume.services.organization.OrganizationService;
import nl.my888.resume.services.people.PersonService;
import org.easymock.EasyMock;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.SimpleThreadScope;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.web.context.WebApplicationContext;

@Configuration
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ResourceAssembler.class))
public class TestServiceConfig {

    @Bean
    public PersonService personService() {
        return EasyMock.createMock(PersonService.class);
    }

    @Bean
    public OrganizationService organizationService() {
        return EasyMock.createMock(OrganizationService.class);
    }

    @Bean
    public CustomScopeConfigurer customScopeConfigurer() {
        // create the request and session scope,
        // see http://stackoverflow.com/questions/2411343/request-scoped-beans-in-spring-testing
        final CustomScopeConfigurer result = new CustomScopeConfigurer();

        final HashMap<String, Object> scopes = new HashMap<>();
        scopes.put(WebApplicationContext.SCOPE_REQUEST, new SimpleThreadScope());
        scopes.put(WebApplicationContext.SCOPE_SESSION, new SimpleThreadScope());
        result.setScopes(scopes);

        return result;
    }
}
