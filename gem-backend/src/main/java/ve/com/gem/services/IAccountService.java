package ve.com.gem.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ve.com.gem.entities.Account;

public interface IAccountService {

	Account save(Account account);
	Account changePassword(Account account);
	Account findById(Long id);
	Account findByUsername(String key);
	Page<Account> findAll(Pageable pageable);
	Page<Account> findByUsernameLike(String key, Pageable pageable);
	void delete(Account object);

}
