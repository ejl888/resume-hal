package nl.my888.resume.hal.config;

import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Import({ServiceContext.class, HalApiConfig.class, PersistenceContext.class})
public class WebAppConfig {
}
