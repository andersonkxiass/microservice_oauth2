package br.com.acs;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.filter.OncePerRequestFilter;


@Configuration
@EnableResourceServer
public class OAuthConfiguration extends ResourceServerConfigurerAdapter {
	
	private static final String RESOURCE_ID = "restservice";

	private TokenExtractor tokenExtractor = new BearerTokenExtractor();

	@Bean
	public AccessTokenConverter accessTokenConverter() {
		return new DefaultAccessTokenConverter();
	}
	
//	@Bean
//	public RemoteTokenServices remoteTokenServices(final @Value("${auth.server.url}") String checkTokenUrl,
//			final @Value("${auth.server.clientId}") String clientId,
//			final @Value("${auth.server.clientsecret}") String clientSecret) {
//		
//		final RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
//		remoteTokenServices.setCheckTokenEndpointUrl(checkTokenUrl);
//		remoteTokenServices.setClientId(clientId);
//		remoteTokenServices.setClientSecret(clientSecret);
//		remoteTokenServices.setAccessTokenConverter(accessTokenConverter());
//		
//		return remoteTokenServices;
//	}
	
	@Override
    public void configure(ResourceServerSecurityConfigurer resources) {
       
		RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setClientId("clientapp");
        tokenService.setClientSecret("123456");
        tokenService.setCheckTokenEndpointUrl("http://localhost:8081/oauth/check_token");

        resources
                .resourceId(RESOURCE_ID)
                .tokenServices(tokenService);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
    	
    	http.addFilterAfter(new OncePerRequestFilter() {
			@Override
			protected void doFilterInternal(HttpServletRequest request,
					HttpServletResponse response, FilterChain filterChain)
					throws ServletException, IOException {
				
				// We don't want to allow access to a resource with no token so clear
				// the security context in case it is actually an OAuth2Authentication
				if (tokenExtractor.extract(request) == null) {
					SecurityContextHolder.clearContext();
				}
				
				filterChain.doFilter(request, response);
			}
		}, AbstractPreAuthenticatedProcessingFilter.class);
    	
		http.csrf().disable();
		http.authorizeRequests().anyRequest().authenticated();
    	
//        http
//            .authorizeRequests()
//            .antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read')")
//            .antMatchers(HttpMethod.OPTIONS, "/**").access("#oauth2.hasScope('read')")
//            .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write')")
//            .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write')")
//            .antMatchers(HttpMethod.PATCH, "/**").access("#oauth2.hasScope('write')")
//            .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write')");
    }
}