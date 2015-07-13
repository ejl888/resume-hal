package nl.my888.resume.hal.config;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import nl.my888.springframework.hateoas.configuration.HalHandlerInstantiator;
import nl.my888.springframework.hateoas.resource.EmbeddedResourceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.hateoas.hal.DefaultCurieProvider;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.hateoas.mvc.TypeConstrainedMappingJackson2HttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static nl.my888.resume.hal.constants.ProfileUtil.toProfileUri;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;

@Configuration
@ComponentScan("nl.my888.resume.hal.resources")
@EnableWebMvc
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class HalApiConfig extends WebMvcConfigurerAdapter {

    public static final String RELATION_PREFIX = "resume888";

    public static final URI COLLECTION_PROFILE =  toProfileUri("collection");

    private static final CurieProvider CURIE_PROVIDER = new DefaultCurieProvider(
            RELATION_PREFIX,
            new UriTemplate("http://www.888ict.nl/rels/{rel}"));


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
//        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public CurieProvider curieProvider() {
        return CURIE_PROVIDER;
    }

    @Autowired
    private RelProvider relProvider;

    @Autowired
    private ObjectMapper halObjectMapper;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        halObjectMapper.configure(com.fasterxml.jackson.databind.SerializationFeature.
                WRITE_DATES_AS_TIMESTAMPS , false);

        halObjectMapper.registerModule(new JodaModule());

        halObjectMapper.registerModule(new Jackson2HalModule());
        halObjectMapper.setHandlerInstantiator(new HalHandlerInstantiator(relProvider, CURIE_PROVIDER));

        MappingJackson2HttpMessageConverter halConverter = new TypeConstrainedMappingJackson2HttpMessageConverter(
                ResourceSupport.class);
        halConverter.setSupportedMediaTypes(Arrays.asList(HAL_JSON));
        halConverter.setObjectMapper(halObjectMapper);

        converters.add(halConverter);
        converters.add(new MappingJackson2HttpMessageConverter());

        // XXX: dirty hack to register curieProvider for EmbbedResourceSupport
        EmbeddedResourceSupport.setCurieProvider(CURIE_PROVIDER);
    }

}
