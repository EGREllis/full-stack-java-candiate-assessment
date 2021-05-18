package org.springframework.samples.petclinic.owner;

import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OwnerDynamicRepository {

	List<Owner> findOwnerByFields(@Param("firstName") String firstName, @Param("lastName") String lastName,
			@Param("address") String address, @Param("city") String city, @Param("telephone") String telephone);

}
