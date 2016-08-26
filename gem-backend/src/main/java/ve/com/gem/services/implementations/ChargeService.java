package ve.com.gem.services.implementations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import ve.com.gem.entities.Charge;
import ve.com.gem.repositories.IChargeRepository;
import ve.com.gem.services.IChargeService;

@Transactional(readOnly = true)
@Service
public class ChargeService implements IChargeService {
	
	@Autowired
	private IChargeRepository repository;

	private List<Charge> objects = new ArrayList<Charge>();
	
	@Transactional(readOnly = false)
	@Override
	public Charge save(Charge object) {
		if(null != object)
		{
			if(null == object.getCreatedAt())
				object.setCreatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
			repository.save(object);
		}
		
		return object;
	}

	@Override
	public Charge findById(Long id) {
		Charge object = repository.findOne(id);
		return object;
	}

	@Override
	public Charge findByName(String key) {
		
		return repository.findByName(key);
	}

	@Override
	public Page<Charge> findAll(Pageable pageable) {
		objects = Lists.newArrayList(repository.findAll(pageable));
		PageImpl<Charge> pages= new PageImpl<>(objects,pageable,repository.count());
		
		return pages;
	}

	@Override
	public Page<Charge> findByNameLike(String key, Pageable pageable) {
		objects = repository.findByNameLike("%"+key+"%", pageable);
		PageImpl<Charge> chargePages= new PageImpl<>(objects, pageable, repository.count());
		return chargePages;
	}

	@Transactional(readOnly = false)
	@Override
	public void delete(Charge object) {
		repository.delete(object);
		
	}

}
