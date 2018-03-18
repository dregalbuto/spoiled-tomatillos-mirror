package edu.northeastern.cs4500.spoiledTomatillos.user.service;

import edu.northeastern.cs4500.spoiledTomatillos.user.model.User;
import edu.northeastern.cs4500.spoiledTomatillos.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

    @Override
    public User findById(int id) {
	    return userRepository.findById(id);
    }

    /**
	 * called by UserRegistration controller to save a user to db
     */
	public void save(User user) {
		userRepository.save(user);
	}
	
	@Override
	public List<User> getAllUsers() {
		return (List<User>) userRepository.findAll();
	}

}
