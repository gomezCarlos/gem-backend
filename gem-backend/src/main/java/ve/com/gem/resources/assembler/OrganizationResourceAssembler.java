package ve.com.gem.resources.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import ve.com.gem.controllers.AccountController;
import ve.com.gem.controllers.OrganizationController;
import ve.com.gem.entities.Organization;
import ve.com.gem.resources.OrganizationResource;

@Component
public class OrganizationResourceAssembler extends ResourceAssemblerSupport<Organization, OrganizationResource> {

	public OrganizationResourceAssembler() {
		super(OrganizationController.class, OrganizationResource.class);
	}
	
	@Override
	public OrganizationResource toResource(Organization organization) {
		OrganizationResource resource = createResourceWithId(organization.getId(), organization);
		resource.setName(organization.getName());
		resource.setDescription(organization.getDescription());
		resource.setCreatedAt(organization.getCreatedAt());
		resource.setUpdatedAt(organization.getUpdatedAt());
		resource.setDeletedAt(organization.getDeletedAt());
		resource.setIsActive(organization.getIsActive());
		//organizationResource.setCompany(organization.getCompany());
		resource.setIds(organization.getId());
		//resource.add(linkTo(OrganizationController.class).slash("").slash(organization.getId()).withSelfRel());
		return resource;
	}

}
