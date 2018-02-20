package edu.northeastern.cs4500.spoiledTomatillos.setup;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import edu.northeastern.cs4500.spoiledTomatillos.data.user.PasswordEncoder;
import edu.northeastern.cs4500.spoiledTomatillos.data.user.Privilege;
import edu.northeastern.cs4500.spoiledTomatillos.data.user.PrivilegeRepository;
import edu.northeastern.cs4500.spoiledTomatillos.data.user.Role;
import edu.northeastern.cs4500.spoiledTomatillos.data.user.RoleRepository;
import edu.northeastern.cs4500.spoiledTomatillos.data.user.User;
import edu.northeastern.cs4500.spoiledTomatillos.data.user.UserRepository;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent>{
	boolean alreadySetup = false;
	 
    @Autowired
    private UserRepository userRepository;
  
    @Autowired
    private RoleRepository roleRepository;
  
    @Autowired
    private PrivilegeRepository privilegeRepository;
  
   // @Autowired
    private PasswordEncoder passwordEncoder;
  
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
  
        if (alreadySetup)
            return;
        Privilege readPrivilege
          = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
          = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
  
        List<Privilege> adminPrivileges 
        		= Arrays.asList(readPrivilege, writePrivilege);        
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));
 
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        
        createUserIfNotFound("Admin", adminRole);
        
        alreadySetup = true;
    }
 
    @Transactional
    private Privilege createPrivilegeIfNotFound(String name) {
  
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }
 
    @Transactional
    private Role createRoleIfNotFound(
      String name, Collection<Privilege> privileges) {
  
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
    
    @Transactional
    private User createUserIfNotFound(String username, Role adminRole) {
    	
    		User admin = userRepository.findFirstOrderByUsername(username);
    		if(admin == null) {
	    		admin = new User();
	    		admin.setFirstName("Test");
	    		admin.setLastName("Test");
	    		admin.setEmail("test@test.com");
	    		admin.setUsername(username);
		    admin.setPassword(passwordEncoder.encode("test"));
	    		admin.setPassword("test");
	    		admin.setRoles(Arrays.asList(adminRole));
	    		admin.setEnabled(true);
	        userRepository.save(admin);
	    		}
    		return admin;
    }
}
