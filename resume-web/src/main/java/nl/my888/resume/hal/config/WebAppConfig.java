package nl.my888.resume.hal.config;

import nl.my888.resume.services.config.ServiceContext;
import org.springframework.context.annotation.Import;


@Import({ServiceContext.class, HalApiConfig.class, PersistenceContext.class})
public class WebAppConfig {
}
