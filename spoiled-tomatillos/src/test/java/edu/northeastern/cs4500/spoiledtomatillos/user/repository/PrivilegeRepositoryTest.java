package edu.northeastern.cs4500.spoiledtomatillos.user.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.northeastern.cs4500.spoiledtomatillos.user.model.Privilege;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PrivilegeRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private PrivilegeRepository privilegeRepository;
	
	@Test
	public void whenFindByName_thenReturnPrivilege() {
		Privilege p = new Privilege("PRIVILEGE_READ");
		entityManager.persist(p);
		entityManager.flush();
		
		Privilege foundP = privilegeRepository.findByName(p.getName());
		
		assertEquals(foundP.getName(), p.getName());
	}
}
