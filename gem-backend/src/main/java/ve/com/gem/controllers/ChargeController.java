package ve.com.gem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ve.com.gem.entities.Charge;
import ve.com.gem.resources.ChargeResource;
import ve.com.gem.resources.assembler.ChargeResourceAssembler;
import ve.com.gem.services.IChargeService;

@RestController
@RequestMapping("/api/v1/charges")
public class ChargeController {

	@Autowired
	private IChargeService service;
	
	@Autowired
	private ChargeResourceAssembler assembler;

	@Autowired
	private PagedResourcesAssembler<Charge> pageAssembler;
	
	
	@RequestMapping(value="",method=RequestMethod.GET,produces="application/hal+json")
	@ResponseBody
	public PagedResources<ChargeResource> loadAll(Pageable pageable){
		
		Page<Charge> objects = service.findAll(pageable);
	
		return pageAssembler.toResource(objects, assembler);
	}
	
	/**
	 * Find one gem.
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<ChargeResource> load(@PathVariable Long id)
	{
		Charge object = service.findById(id);
		if(null == object)
		{
			return new ResponseEntity<ChargeResource>(assembler.toResource(object),HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<ChargeResource>(assembler.toResource(object),HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="",method=RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResponseEntity<ChargeResource> save(@RequestBody Charge object)
	{
		if(service.save(object)!=null)
		{
			return new ResponseEntity<ChargeResource>(assembler.toResource(object),HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<ChargeResource>(assembler.toResource(object),HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT, produces = "application/json; charset=UTF-8")
	public ResponseEntity<ChargeResource> update(@PathVariable Long id,@RequestBody Charge object)
	{
		Charge search = service.findById(id);
		if(null == search)
		{
			return new ResponseEntity<ChargeResource>(assembler.toResource(search),HttpStatus.NOT_FOUND);
		}else
		
		if(null != object )
		{
			object.setId(id);
			service.save(object);
			return new ResponseEntity<ChargeResource>(assembler.toResource(search),HttpStatus.OK);
		}
		else
			return new ResponseEntity<ChargeResource>(assembler.toResource(search),HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE,produces = "application/json; charset=UTF-8")
	public ResponseEntity<Charge> delete(@PathVariable Long id){
		Charge search = service.findById(id);
		System.out.println(search);
		if(null != search)
		{
			service.delete(search);
			return new ResponseEntity<Charge>(HttpStatus.OK);
		}
		else
		{
				return new ResponseEntity<Charge>(HttpStatus.BAD_REQUEST);
		}
	}
}
