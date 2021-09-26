package telemach.rest.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IServiceRepository extends JpaRepository<Service, Integer>{
	
	Service findOneById(Integer id);
	
	Service findOneByAddressIdAndService(Integer addId, String service);
	
	List<Service> findAllByAddressId(Integer id);
}
