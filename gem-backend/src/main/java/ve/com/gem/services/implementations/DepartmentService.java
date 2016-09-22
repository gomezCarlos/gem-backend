package ve.com.gem.services.implementations;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import ve.com.gem.entities.Department;
import ve.com.gem.repositories.IDepartmentRepository;
import ve.com.gem.services.IDepartmentService;

@Transactional(readOnly=true)
@Service
public class DepartmentService implements IDepartmentService {
	
	@Autowired
	IDepartmentRepository repository;
    List<Department> objects = new ArrayList<Department>();
    
	public DepartmentService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Page<Department> findAll(Pageable pageable) {
		
		objects = Lists.newArrayList(repository.findAll(pageable));
		PageImpl<Department> pages= new PageImpl<>(objects,pageable,repository.count());
		
	return pages;
	}
	

	@Override
	public List<Department> search(String key) {
		return repository.findByNameLike("%"+key+"%");
	}

	@Override
	public void addByName(String name) {
		// TODO Auto-generated method stub
	}

	@Transactional(readOnly=false)
	@Override
	public Department save(Department object) {
		if(null != object)
		{
			if(null == object.getCreatedAt())
				object.setCreatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
			repository.save(object);
		}
		
		return object;
	}

	@Override
	public Department findById(Long id) {
		Department object = repository.findOne(id);
		return object;
	}

	@Override
	public Page<Department> findAll(Sort sort) {
		 PageImpl<Department> pages= new PageImpl<Department>(Lists.newArrayList(repository.findAll(sort)));
		 return pages;
	}

	@Override
	@Transactional(readOnly=false)
	public boolean delete(Department object) {
		Long id=0L;
		if(null != object){
			System.out.println("No es nula.");
			id=object.getId();
		}
		repository.delete(id);
		System.out.println(repository.exists(id));
		return !repository.exists(id);
	}
}
