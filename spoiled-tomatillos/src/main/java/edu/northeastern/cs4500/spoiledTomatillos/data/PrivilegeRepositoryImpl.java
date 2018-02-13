package edu.northeastern.cs4500.spoiledTomatillos.data;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class PrivilegeRepositoryImpl implements PrivilegeRepositoryCustom {
	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public Privilege findByName(String name) {
		Query query = entityManager.createNativeQuery("SELECT * FROM app.roles" 
				+ " WHERE name LIKE ?", Privilege.class);
		query.setParameter(1, name);
		return (Privilege) query.getSingleResult();
	}

}
