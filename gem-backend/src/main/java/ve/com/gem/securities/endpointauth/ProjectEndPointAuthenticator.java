package ve.com.gem.securities.endpointauth;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.google.common.base.Splitter;

import ve.com.gem.repositories.IEndPointServiceAuthenticationRepository;

@Component("projectEndPointAuthenticator")
public class ProjectEndPointAuthenticator {
	
	@Autowired
	private IEndPointServiceAuthenticationRepository endPointServiceAuthenticationRepository;

	public boolean hasPermissionCustomizedForProjects(Long dbCode) {

		if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
			return false;

        Iterable<String> listOfAuthorities = Splitter.on(',')
       	       .trimResults()
       	       .omitEmptyStrings()
       	       .split(endPointServiceAuthenticationRepository.findOne(dbCode).getAuthorities());
		
		
		Collection<? extends GrantedAuthority> grantedAuthorityList = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for (GrantedAuthority grantedAuthority : grantedAuthorityList) {
			for (String authority : listOfAuthorities) {
				System.out.println("Authority: " + authority);
				System.out.println("grantedAuthority: " + grantedAuthority);
				if (grantedAuthority.toString().equals(authority))
					return true;
				else
					continue;
			}
				
		}		
		return false;
	}
}
