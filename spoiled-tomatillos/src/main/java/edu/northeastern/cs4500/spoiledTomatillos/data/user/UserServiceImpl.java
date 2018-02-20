package edu.northeastern.cs4500.spoiledTomatillos.data.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User getUserByUsername(String username) {
		return userRepository.findFirstOrderByUsername(username);
	}
	
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

}
