package nl.my888.resume.hal.config;

import java.util.Properties;
import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.hateoas.hal.DefaultCurieProvider;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan("nl.my888")
@EnableWebMvc
@EnableTransactionManagement
@EnableJpaRepositories("nl.my888.resume.repository")
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class WebAppConfig extends WebMvcConfigurerAdapter {

//    @Resource
//    private Environment environment;

    public static final String RELATION_PREFIX = "resume888";
//    public static final URI COLLECTION_PROFILE_URI = toProfileUri("collectie");

    private final CurieProvider curieProvider = new DefaultCurieProvider(
            RELATION_PREFIX,
            new UriTemplate("http://www.ilent.nl/rels/{rel}"));


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
