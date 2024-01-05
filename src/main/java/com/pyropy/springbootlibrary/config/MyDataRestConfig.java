package com.pyropy.springbootlibrary.config;

import com.pyropy.springbootlibrary.entity.Book;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


/**
 * this file is used to disable certain methods on the auto generated
 * endpoints by spring data rest
 */

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    private String theAllowedOrigins = "http://localhost:3000";

    @Override
    public void configureRepositoryRestConfiguration (RepositoryRestConfiguration config, CorsRegistry cors) {
        //create an array of the http methods to disable
        HttpMethod[] theUnsupportedActions = {HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH, HttpMethod.PUT};

        config.exposeIdsFor(Book.class);

        disableHttpMethods(Book.class, config, theUnsupportedActions);

//        Configure CORS mapping

        cors.addMapping(config.getBasePath() + "/**")
                .allowedOrigins(theAllowedOrigins);
    }

    private void disableHttpMethods(Class<Book> bookClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(bookClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }
}
