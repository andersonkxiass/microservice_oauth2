package br.com.acs.controller;

import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.acs.model.UserAccount;
import br.com.acs.repository.UserRepository;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserResource {

//	private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<UserAccount> getAllUsers() {
		return userRepository.findAll();
	}
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserAccount getOneUser(@PathVariable("userId") String userId) {
        
		UserAccount user = userRepository.findOne(userId);
       
        if (user == null) {
           // throw new StoryNotFoundException(storyId);
        }
        
        return user;
    }

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<UserAccount> createAccount( @RequestBody UserAccount user) {

		userRepository.save(user);
		
		return new ResponseEntity<UserAccount>(user, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<UserAccount> updateUser( @PathVariable("userId") String userId, @RequestBody UserAccount user) {

		if(userRepository.findOne(userId) != null){
			
			userRepository.save(user);
			return new ResponseEntity<UserAccount>(user, HttpStatus.OK);
			
		}else {
			
			return new ResponseEntity<UserAccount>(user, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<UserAccount> deleteAccount( @RequestBody UserAccount user) {

		userRepository.delete(user);
		
		return new ResponseEntity<UserAccount>(user, HttpStatus.CREATED);
	}
}