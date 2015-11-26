package br.com.acs;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableMongoRepositories
@ComponentScan(basePackages={"br.com.acs"})
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }
   
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
	@Bean
	public Docket petApi() {
	    return new Docket(DocumentationType.SWAGGER_2)
	        .select()
	          .apis(RequestHandlerSelectors.any())
	          .paths(PathSelectors.any())
	          .build()
	        .pathMapping("/")
	        .directModelSubstitute(LocalDate.class,
	            String.class)
	        .genericModelSubstitutes(ResponseEntity.class)
	        .alternateTypeRules(
	            newRule(typeResolver.resolve(DeferredResult.class,
	                    typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
	                typeResolver.resolve(WildcardType.class)))
	        .useDefaultResponseMessages(false)
	        .globalResponseMessage(RequestMethod.GET,
	            newArrayList(new ResponseMessageBuilder()
	                .code(500)
	                .message("500 message")
	                .responseModel(new ModelRef("Error"))
	                .build()))
	        .securitySchemes(newArrayList(apiKey()))
	        .securityContexts(newArrayList(securityContext()))
	        .enableUrlTemplating(true);
	  }

	  @Autowired
	  private TypeResolver typeResolver;

	  private ApiKey apiKey() {
	    return new ApiKey("mykey", "api_key", "header");
	  }

	  private SecurityContext securityContext() {
	    return SecurityContext.builder()
	        .securityReferences(defaultAuth())
	        .forPaths(PathSelectors.regex("/anyPath.*"))
	        .build();
	  }

	  List<SecurityReference> defaultAuth() {
	    AuthorizationScope authorizationScope
	        = new AuthorizationScope("global", "accessEverything");
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	    authorizationScopes[0] = authorizationScope;
	    return newArrayList(
	        new SecurityReference("mykey", authorizationScopes));
	  }
//
//	  @Bean
//	  SecurityConfiguration security() {
//	    return new SecurityConfiguration(
//	        "test-app-client-id",
//	        "test-app-client-secret",
//	        "test-app-realm",
//	        "test-app",
//	        "apiKey",
//	        "," /*scope separator*/);
//	  }

	  @Bean
	  UiConfiguration uiConfig() {
	    return new UiConfiguration(
	        "validatorUrl");
	  }
}