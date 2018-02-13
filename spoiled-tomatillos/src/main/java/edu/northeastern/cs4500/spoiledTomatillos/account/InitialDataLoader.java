package edu.northeastern.cs4500.spoiledTomatillos.account;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import edu.northeastern.cs4500.spoiledTomatillos.data.PasswordEncoder;
import edu.northeastern.cs4500.spoiledTomatillos.data.Privilege;
import edu.northeastern.cs4500.spoiledTomatillos.data.PrivilegeRepository;
import edu.northeastern.cs4500.spoiledTomatillos.data.Role;
import edu.northeastern.cs4500.spoiledTomatillos.data.RoleRepository;
import edu.northeastern.cs4500.spoiledTomatillos.data.User;
import edu.northeastern.cs4500.spoiledTomatillos.data.UserRepository;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent>{
	boolean alreadySetup = false;
	 
    @Autowired
    private UserRepository userRepository;
  
    @Autowired
    private RoleRepository roleRepository;
  
    @Autowired
    private PrivilegeRepository privilegeRepository;
  
    //@Autowired
    //private PasswordEncoder passwordEncoder;
  
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
  
        if (alreadySetup)
            return;
        Privilege readPrivilege
          = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
          = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
  
        List<Privilege> adminPrivileges = Arrays.asList(
          readPrivilege, writePrivilege);        
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));
 
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        //user.setPassword(passwordEncoder.encode("test"));
        user.setPassword("test");
        user.setEmail("test@test.com");
        user.setRoles(Arrays.asList(adminRole));
        user.setEnabled(true);
        System.out.println("Saving user");
        System.out.println(user.toString());
        userRepository.save(user);
 
        alreadySetup = true;
    }
 
    @Transactional
    private Privilege createPrivilegeIfNotFound(String name) {
  
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            System.out.println("Saving privilege");
            System.out.println(privilege.toString());
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
            System.out.println("Saving role");
            System.out.println(role.toString());
            roleRepository.save(role);
        }
        return role;
    }
}
