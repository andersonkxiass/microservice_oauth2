package br.com.acs.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.acs.model.UserAccount;

public interface UserRepository extends MongoRepository<UserAccount, String> {
	
    public UserAccount findByUsername(String username);
    
    public UserAccount findByUsernameAndPassword(String username, String password);
}