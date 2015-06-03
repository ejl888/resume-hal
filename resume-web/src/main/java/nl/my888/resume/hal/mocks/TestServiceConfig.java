package nl.my888.resume.hal.mocks;

import java.util.HashMap;

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
    public CustomScopeConfigurer customScopeConfigurer() {
        // maak de scope's aan zie http://stackoverflow.com/questions/2411343/request-scoped-beans-in-spring-testing
        final CustomScopeConfigurer result = new CustomScopeConfigurer();

        final HashMap<String, Object> scopes = new HashMap<>();
        scopes.put(WebApplicationContext.SCOPE_REQUEST, new SimpleThreadScope());
        scopes.put(WebApplicationContext.SCOPE_SESSION, new SimpleThreadScope());
        result.setScopes(scopes);

        return result;
    }
}
