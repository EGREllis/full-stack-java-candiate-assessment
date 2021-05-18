package org.springframework.samples.petclinic.owner;

import org.apache.catalina.User;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class OwnerDynamicRepositoryImpl implements OwnerDynamicRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Owner> findOwnerByFields(String firstName, String lastName, String address, String city,
			String telephone) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Owner> query = cb.createQuery(Owner.class);
		Root<Owner> root = query.from(Owner.class);

		List<Predicate> predicates = new ArrayList<>(5);
		if (firstName != null && firstName.length() > 0) {
			predicates.add(cb.equal(root.get("firstName"), firstName));
		}
		if (lastName != null && lastName.length() > 0) {
			predicates.add(cb.equal(root.get("lastName"), lastName));
		}
		if (address != null && address.length() > 0) {
			predicates.add(cb.equal(root.get("address"), address));
		}
		if (city != null && city.length() > 0) {
			predicates.add(cb.equal(root.get("city"), city));
		}
		if (telephone != null && telephone.length() > 0) {
			predicates.add(cb.equal(root.get("telephone"), telephone));
		}

		if (predicates.size() == 0) {
			// This would happen if all the search fields were blank when the form was submitted
		}
		else if (predicates.size() == 1) {
			query.where(predicates.get(0));
		}
		else {
			// Chain predicates together as and?
			Predicate combined = cb.and(predicates.get(0), predicates.get(1));
			for (int i = 2; i < predicates.size(); i++) {
				combined = cb.and(combined, predicates.get(i));
			}
			query.where(combined);
		}

		return entityManager.createQuery(query).getResultList();
	}

}
