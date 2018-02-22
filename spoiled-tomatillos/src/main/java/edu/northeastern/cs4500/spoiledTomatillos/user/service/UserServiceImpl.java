package edu.northeastern.cs4500.spoiledTomatillos.user.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.northeastern.cs4500.spoiledTomatillos.user.model.Role;
import edu.northeastern.cs4500.spoiledTomatillos.user.model.User;
import edu.northeastern.cs4500.spoiledTomatillos.user.repository.UserRepository;
import edu.northeastern.cs4500.spoiledTomatillos.web.dto.UserRegistrationDto;

/**
 * Implementation of UserService with methods to work with Spring Security
 * 
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }
	
	public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public User save(UserRegistrationDto registration){
        User user = new User();
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setEmail(registration.getEmail());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setRoles(Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
	
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

}
