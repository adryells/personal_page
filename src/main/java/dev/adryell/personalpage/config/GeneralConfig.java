package dev.adryell.personalpage.config;


import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;


@Configuration
@PropertySource("classpath:application.properties")
public class GeneralConfig implements EnvironmentAware {

    static Environment environment;

    @Override
    public void setEnvironment(final Environment environment) {
        GeneralConfig.environment = environment;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public static String getGCP_BUCKET_NAME() {
        return environment.getProperty("gcp.GCP_BUCKET_NAME");
    }

    public static String getGCP_PUBLIC_URL() {
        return environment.getProperty("gcp.GCP_PUBLIC_URL");
    }

    public static String getGcpPrefixUrl() {
        return environment.getProperty("gcp.GCP_PUBLIC_URL") + environment.getProperty("gcp.GCP_BUCKET_NAME");
    }
}
