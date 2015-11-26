package br.com.acs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import br.com.acs.services.MyUserDetailsService;
import br.com.acs.utils.ApiPaths;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MyUserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/*custom roles*/
		http.httpBasic().and()
        .exceptionHandling().and()
        .anonymous().and()
        .servletApi().and()
        .headers().cacheControl().and()
        .authorizeRequests()

        // Allow anonymous resource requests
        .antMatchers("/").permitAll()
        .antMatchers(ApiPaths.LOGIN +"/**").permitAll()
        .antMatchers(ApiPaths.USER +"/**").hasRole("USER")
        .antMatchers(ApiPaths.GAMER +"/**").hasRole("USER")
        // Allow anonymous logins
        //.antMatchers("/auth/**").permitAll()
        // All other request need to be authenticated
        .anyRequest().authenticated().and();
		http.csrf().disable();
		/*custom roles*/
		
//		http
//	      .httpBasic().and()
//	      .authorizeRequests()
//	      .antMatchers(HttpMethod.POST, "/employees").hasRole("ADMIN")
//	      .antMatchers(HttpMethod.PUT, "/employees/**").hasRole("ADMIN")
//	      .antMatchers(HttpMethod.PATCH, "/employees/**").hasRole("ADMIN").and()
//	      .csrf().disable();

	}
}