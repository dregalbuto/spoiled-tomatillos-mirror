package edu.northeastern.cs4500.spoiledtomatillos.user.repository;

import edu.northeastern.cs4500.spoiledtomatillos.user.model.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoleRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void whenFindByName_thenReturnRole() {
        Role r = new Role("ROLE_ADMIN");
        entityManager.persist(r);
        entityManager.flush();

        Role foundR = roleRepository.findByName(r.getName());

        assertEquals(foundR.getName(), r.getName());
    }
}
