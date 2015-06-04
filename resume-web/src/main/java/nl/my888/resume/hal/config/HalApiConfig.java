package nl.my888.resume.hal.config;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.my888.springframework.hateoas.configuration.HalHandlerInstantiator;
import nl.my888.springframework.hateoas.resource.EmbeddedResourceSupport;
import org.hibernate.ejb.HibernatePersistence;
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
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static org.springframework.hateoas.MediaTypes.HAL_JSON;

@Configuration
@ComponentScan("nl.my888")
@EnableWebMvc
//@EnableJpaRepositories("nl.my888.resume.repository")
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class HalApiConfig extends WebMvcConfigurerAdapter {

    public static final String RELATION_PREFIX = "resume888";

    private static final CurieProvider CURIE_PROVIDER = new DefaultCurieProvider(
            RELATION_PREFIX,
            new UriTemplate("http://www.888ict.nl/rels/{rel}"));

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


    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource result = new DriverManagerDataSource();

        result.setDriverClassName("com.mysql.jdbc.Driver");
        result.setUrl("jdbc:mysql://localhost/test");
        result.setUsername("root");
        //result.setPassword("");

        return result;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean result = new LocalContainerEntityManagerFactoryBean();

        result.setDataSource(dataSource());
        result.setPersistenceProviderClass(HibernatePersistence.class);
        result.setPackagesToScan("nl.my888.resume.repository");
        result.setJpaProperties(jpaProperties());

        return result;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        final JpaTransactionManager result = new JpaTransactionManager();

        result.setEntityManagerFactory(entityManagerFactory().getObject());

        return result;
    }

    private Properties jpaProperties() {
        final Properties result = new Properties();

        result.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        result.setProperty("hibernate.show_sql", "true");
        result.setProperty("hibernate.hbm2ddl.auto", "update");

        return result;
    }
}
