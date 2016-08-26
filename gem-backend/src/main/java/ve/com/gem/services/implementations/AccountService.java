package ve.com.gem.services.implementations;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import ve.com.gem.entities.Account;
import ve.com.gem.entities.Charge;
import ve.com.gem.entities.Department;
import ve.com.gem.repositories.IAccountRepository;
import ve.com.gem.repositories.IChargeRepository;
import ve.com.gem.repositories.IDepartmentRepository;
import ve.com.gem.services.IAccountService;

@Transactional(readOnly = true)
@Service
public class AccountService implements IAccountService {
	
	List<Account> accounts = new ArrayList<Account>(); 

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IAccountRepository accountRepository;
    
    @Autowired
    private IDepartmentRepository departmentRepository;
    
    @Autowired
    private IChargeRepository chargeRepository;
    
    
    //@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @Transactional(readOnly = false)
    @Override
    public Account save(Account account) {
    	if (account.getPassword() != null) {
    		account.setPassword(passwordEncoder.encode(account.getPassword()));
    		account.setIsActive(true);
    		
    	}
    	
    	if (account.getId() != null) {
    		account.setId(account.getId());
    	}
    	
        accountRepository.save(account);
        return account;
        
    }

	@Override
	public Page<Account> findAll(Pageable pageable) {
		accounts = Lists.newArrayList(accountRepository.findAll(pageable));
		PageImpl<Account> accountPages= new PageImpl<>(accounts, pageable, accountRepository.count());
		return accountPages;
	}

	@Override
	public Page<Account> findByUsernameLike(String key, Pageable pageable) {
		accounts = accountRepository.findByUsernameLike("%"+key+"%", pageable);
		PageImpl<Account> accountPages= new PageImpl<>(accounts, pageable, accountRepository.count());
		return accountPages;
	}

	@Override
	public Account findById(Long id) {
		return accountRepository.findOne(id);
	}

	@Override
	public Account findByUsername(String key) {
		return accountRepository.findByUsername(key);
	}

	@Transactional(readOnly=false)
	@Override
	public void delete(Account object) {
		accountRepository.delete(object);
		
	}

	@Transactional(readOnly=false)
	@Override
	public Account changePassword(Account account) {
		account.setId(account.getId());
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		account.setIsActive(true);
		account.setLastPasswordReset(Timestamp.valueOf(LocalDateTime.now()));
		System.out.println("service last pass: " + account.getLastPasswordReset());
		return account;
	}
    
}
