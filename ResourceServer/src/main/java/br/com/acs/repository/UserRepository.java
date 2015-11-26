package br.com.acs.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import br.com.acs.model.UserAccount;

public interface UserRepository extends MongoRepository<UserAccount, String> {
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserAccount> findAll();
	
    public UserAccount findByUsername(String username);
    
    public UserAccount findByUsernameAndPassword(String username, String password);
}