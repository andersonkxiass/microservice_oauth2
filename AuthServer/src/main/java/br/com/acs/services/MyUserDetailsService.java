package br.com.acs.services;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.acs.model.UserAccount;
import br.com.acs.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserAccount person = userRepository.findByUsername(username);
		
		if (person == null) {
			throw new UsernameNotFoundException("Username " + username + " notfound");
		}
		
		return new User(username, person.getPassword(), getGrantedAuthorities(username));
	}

	private Collection<? extends GrantedAuthority> getGrantedAuthorities(String username) {
		
		Collection<? extends GrantedAuthority> authorities;
		
		if (username.equals("anderson_admin")) {
			
			authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
			
		} else {
			
			authorities = AuthorityUtils.createAuthorityList("ROLE_BASIC","ROLE_USER");
		}
		
		return authorities;
	}
}