package telemach.rest.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface IAddressRepository extends JpaRepository<Address, Integer>{
	
	
	Address findOneById(Integer addressId);
	
	List<Address> findAllByIdIn(List<Integer> ids);
}
