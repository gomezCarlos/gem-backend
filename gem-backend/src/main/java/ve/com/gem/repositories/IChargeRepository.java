package ve.com.gem.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import ve.com.gem.entities.Charge;

@Repository
public interface IChargeRepository extends PagingAndSortingRepository<Charge, Long>{

	Charge findByName(String key);

	List<Charge> findByNameLike(String key, Pageable pageable);

}
