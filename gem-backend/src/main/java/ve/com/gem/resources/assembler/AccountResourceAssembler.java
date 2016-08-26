package ve.com.gem.resources.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import ve.com.gem.controllers.AccountController;
import ve.com.gem.controllers.ChargeController;
import ve.com.gem.controllers.DepartmentController;
import ve.com.gem.controllers.OrganizationController;
import ve.com.gem.entities.Account;
import ve.com.gem.resources.AccountResource;

@Component
public class AccountResourceAssembler extends ResourceAssemblerSupport<Account, AccountResource>{

	public AccountResourceAssembler () {
		super(AccountController.class, AccountResource.class);
	}

	@Override
	public AccountResource toResource(Account account) {
		AccountResource resource = new AccountResource();
		resource.setUsername(account.getUsername());
		resource.setPassword(account.getPassword());
		resource.setAuthorities(account.getAuthorities());
		resource.setFirstname(account.getFirstname());
		resource.setLastname(account.getLastname());
		resource.setDepartment(account.getDepartment());
		resource.setCharge(account.getCharge());
		resource.setOrganization(account.getOrganization());
		resource.setIds(account.getId());
	    resource.add(linkTo(AccountController.class).slash("").slash(account.getId()).withSelfRel());
	    
	    if (null != account.getDepartment())
	    	resource.add(linkTo(DepartmentController.class).slash("").slash(account.getDepartment().getId()).withRel("department"));
	    
	    if (null != account.getCharge())
	    	resource.add(linkTo(ChargeController.class).slash("").slash(account.getCharge().getId()).withRel("charge"));
	    
	    if (null != account.getOrganization())
	    	resource.add(linkTo(OrganizationController.class).slash("").slash(account.getOrganization().getId()).withRel("organization"));
	    
	    return resource;
	}
}
