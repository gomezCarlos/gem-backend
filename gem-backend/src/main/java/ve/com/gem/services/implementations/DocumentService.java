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

import ve.com.gem.entities.Document;
import ve.com.gem.repositories.IDocumentRepository;
import ve.com.gem.services.IDocumentService;

@Transactional(readOnly=true)
@Service
public class DocumentService implements IDocumentService {
	
	@Autowired
	IDocumentRepository repository;
    List<Document> objects = new ArrayList<Document>();
    
	public DocumentService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Page<Document> findAll(Pageable pageable) {
		
		objects = Lists.newArrayList(repository.findAll(pageable));
		PageImpl<Document> pages= new PageImpl<>(objects,pageable,repository.count());
		
	return pages;
	}
	

	@Override
	public List<Document> search(String key) {
		return repository.findByNameLike("%"+key+"%");
	}

	@Override
	public void addByName(String name) {
		// TODO Auto-generated method stub
	}

	@Transactional(readOnly=false)
	@Override
	public Document save(Document object) {
		if(null != object)
		{
			if(null == object.getCreatedAt())
				object.setCreatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
			repository.save(object);
		}
		
		return object;
	}

	@Override
	public Document findById(Long id) {
		Document object = repository.findOne(id);
		return object;
	}

	@Override
	public Page<Document> findAll(Sort sort) {
		 PageImpl<Document> pages= new PageImpl<Document>(Lists.newArrayList(repository.findAll(sort)));
		 return pages;
	}

	@Override
	@Transactional(readOnly=false)
	public boolean delete(Document object) {
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
