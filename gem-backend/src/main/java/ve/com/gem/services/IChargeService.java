package ve.com.gem.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ve.com.gem.entities.Charge;

public interface IChargeService {

	Charge save(Charge object);
	Charge findById(Long id);
	Charge findByName(String key);
	Page<Charge> findAll(Pageable pageable);
	Page<Charge> findByNameLike(String key, Pageable pageable);
	void delete(Charge object);
}
