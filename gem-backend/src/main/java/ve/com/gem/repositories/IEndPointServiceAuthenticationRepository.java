package ve.com.gem.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import ve.com.gem.entities.EndPointServiceAuthentication;

@Repository
public interface IEndPointServiceAuthenticationRepository extends PagingAndSortingRepository<EndPointServiceAuthentication, Long>{
	public EndPointServiceAuthentication findOneByName(String name); 
	
}
