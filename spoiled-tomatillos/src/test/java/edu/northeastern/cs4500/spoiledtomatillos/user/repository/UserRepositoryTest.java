package edu.northeastern.cs4500.spoiledtomatillos.user.repository;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;
import edu.northeastern.cs4500.spoiledtomatillos.user.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void whenFindByUsername_thenReturnUser() {
		User diana = new User();
		diana.setUsername("diana");
		entityManager.persist(diana);
		entityManager.flush();
		
		User found = userRepository.findByUsername(diana.getUsername());
		
		assertEquals(found.getUsername(), diana.getUsername());
	}
	
	@Test
	public void whenFindByEmail_thenReturnUser() {
		User diana = new User();
		diana.setEmail("regalbuto.d@husky.neu.edu");
		entityManager.persist(diana);
		entityManager.flush();
		User found = userRepository.findByEmail(diana.getEmail());
		assertEquals(found.getEmail(), diana.getEmail());
	}
	
}
