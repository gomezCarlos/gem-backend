package ve.com.gem.controllers;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ve.com.gem.entities.Account;
import ve.com.gem.resources.AccountResource;
import ve.com.gem.resources.assembler.AccountResourceAssembler;
import ve.com.gem.services.IAccountService;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/accounts")
public class AccountController {

	@Autowired
	private IAccountService service;

	@Autowired
	private AccountResourceAssembler assembler;

	@Autowired
	private PagedResourcesAssembler<Account> pageAssembler;
	

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<AccountResource> saveAccount(@RequestBody Account account) {

		if (null != account && service.findByUsername(account.getUsername()) == null) {

			service.save(account);
			return new ResponseEntity<AccountResource>(assembler.toResource(account), HttpStatus.CREATED);
		}

		else if (service.findByUsername(account.getUsername()) != null) {

			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		else {
			return new ResponseEntity<AccountResource>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public PagedResources<AccountResource> loadAll(Pageable pageable) {

		Page<Account> accounts = service.findAll(pageable);
		return pageAssembler.toResource(accounts, assembler);
	}

	@RequestMapping(value = "search/findByUsernameLike/{username}/", method = RequestMethod.GET)
	public PagedResources<AccountResource> loadAll(@PathVariable String username, Pageable pageable) {

		if (null != username) {
			Page<Account> accounts = service.findByUsernameLike(username, pageable);
			return pageAssembler.toResource(accounts, assembler);
		} else {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return null;
		}
	}
	
	@RequestMapping(value = "/current", method = RequestMethod.GET)
	public ResponseEntity<AccountResource> getCurrentUser(Pageable pageable) {
		
		Account account = service.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		return new ResponseEntity<AccountResource>(assembler.toResource(account), HttpStatus.OK);
	}

	@RequestMapping(value = "search/findByUsername/{username}/", method = RequestMethod.GET)
	public ResponseEntity<AccountResource> findByUsername(@PathVariable String username) {

		if (null != username) {
			Account account = service.findByUsername(username);
			if (null != account) {

				return new ResponseEntity<AccountResource>(assembler.toResource(account), HttpStatus.OK);
				
			} else {
				return new ResponseEntity<AccountResource>(HttpStatus.NOT_FOUND);
			}
			
		} else {
			return new ResponseEntity<AccountResource>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<AccountResource> findById(@PathVariable Long id) {

		if (null != id) {

			Account account = service.findById(id);
			if (null != account) {

				return new ResponseEntity<AccountResource>(assembler.toResource(account), HttpStatus.OK);
			}
		}

		else {

			return new ResponseEntity<AccountResource>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<AccountResource>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<AccountResource> updateAccount(@PathVariable Long id, @RequestBody Account account) {

		Account accountSearch = service.findById(id);

		if (null == accountSearch) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if (null != account) {
			
			account.setId(id);
			service.save(account);
			return new ResponseEntity<AccountResource>(assembler.toResource(account), HttpStatus.OK);
		}

		else {
			
			return new ResponseEntity<AccountResource>(assembler.toResource(account),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.PUT)
	public ResponseEntity<AccountResource> updatePassword(@RequestBody Account account) {

		Account accountSearch = service.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		if (null == accountSearch) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if (null != account) {
			
			accountSearch.setPassword(account.getPassword());
			service.changePassword(accountSearch);
			
			return new ResponseEntity<AccountResource>(assembler.toResource(accountSearch), HttpStatus.OK);
		}

		else {
			
			return new ResponseEntity<AccountResource>(assembler.toResource(account),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE,produces = "application/json; charset=UTF-8")
	public ResponseEntity<Account> delete(@PathVariable Long id){
		Account search = service.findById(id);
		System.out.println(search);
		if(null != search)
		{
			service.delete(search);
			return new ResponseEntity<Account>(HttpStatus.OK);
		}
		else
		{
				return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
		}
	}
}

