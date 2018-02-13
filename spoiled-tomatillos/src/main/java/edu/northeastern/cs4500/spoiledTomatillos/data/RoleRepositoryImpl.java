package edu.northeastern.cs4500.spoiledTomatillos.data;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class RoleRepositoryImpl implements RoleRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public Role findByName(String name) {
		Query query = entityManager.createNativeQuery("SELECT * FROM app.roles" 
				+ " WHERE name LIKE ?", Role.class);
		query.setParameter(1, name);
		return (Role) query.getSingleResult();
	}

}
