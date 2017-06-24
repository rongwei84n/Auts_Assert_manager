package com.phicomm.smarthome.sharedwifi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig
 */
@EnableWebMvc
@EnableSwagger2
@Configuration
@PropertySources(@PropertySource(value = "classpath:server.properties", ignoreResourceNotFound = true))
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    /**
     * get configuration from file(application.properties)
     */
    @Value("${SWAGGER_SCAN_BASE_PACKAGE_PATH}")
    private String SWAGGER_SCAN_BASE_PACKAGE_PATH;

    @Value("${SERVICE_GROUP_NAME_STR}")
    private String SERVICE_GROUP_NAME_STR;

    @Value("${SERVICE_TITLE_NAME_STR}")
    private String SERVICE_TITLE_NAME_STR;

    @Value("${SERVICE_DESCRIPTION_STR}")
    private String SERVICE_DESCRIPTION_STR;

    @Value("${SERVICE_FRAMEWORK_VERSION_STR}")
    private String SERVICE_FRAMEWORK_VERSION_STR;

    @Value("${SERVICE_CONTACT_USERNAME_STR}")
    private String SERVICE_CONTACT_USERNAME_STR;

    @Value("${SERVICE_CONTACT_USERURL_STR}")
    private String SERVICE_CONTACT_USERURL_STR;

    @Value("${SERVICE_CONTACT_USERMAIL_STR}")
    private String SERVICE_CONTACT_USERMAIL_STR;

    @Value("${SERVICE_LICENSE_STR}")
    private String SERVICE_LICENSE_STR;

    @Value("${SERVICE_LICENSE_URL}")
    private String SERVICE_LICENSE_URL;

    /**
     * Swagger框架的自定义配置
     * [Debug] 此处修改影响全局静态资源路径配置，各团队开发时需特别注意
     * Author: liang.zhang at 20170518
     * */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }

    @Bean
    public Docket createRestApi()
    {
        Docket curSwaggerConfigDocket = new Docket(DocumentationType.SWAGGER_2)
                .groupName(SERVICE_GROUP_NAME_STR)                                             /** set SwaggerUI current project(Service) name */
                .useDefaultResponseMessages(false)                                             /** refuse the default response mode */
                .forCodeGeneration(true)                                                       /** set good type for API Document code */
                .select()                                                                      /** set the path where build the document */
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE_PATH))     /** set the path for the scanning of API,default as apis(RequestHandlerSelectors.any()) */
                .paths(PathSelectors.any())                                                    /** monitoring the api state */
                .build()
                .apiInfo(testApiInfo());
        return curSwaggerConfigDocket;
    }

    private ApiInfo testApiInfo() {
        return new ApiInfoBuilder()
                .title(SERVICE_GROUP_NAME_STR)
                .description(SERVICE_DESCRIPTION_STR)
                .version(SERVICE_FRAMEWORK_VERSION_STR)
                .termsOfServiceUrl(SERVICE_CONTACT_USERURL_STR)
                .contact(new Contact(SERVICE_CONTACT_USERNAME_STR, SERVICE_CONTACT_USERURL_STR,SERVICE_CONTACT_USERMAIL_STR))
                .license(SERVICE_LICENSE_STR)
                .licenseUrl(SERVICE_LICENSE_URL)
                .build();
    }
}

