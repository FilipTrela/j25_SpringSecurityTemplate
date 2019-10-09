package pl.sda.gdajava25.spring.security.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.gdajava25.spring.security.model.Account;
import pl.sda.gdajava25.spring.security.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountRoleServis accountRoleServis;
    private final PasswordEncoder passwordEncoder;

    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    public boolean register(Account account) {
        if (accountRepository.existsByUsername(account.getUsername())) {
            return false;
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setAccountRoles(accountRoleServis.getDefaultRoles());

        accountRepository.save(account);

        return true;
    }

    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    public void disable(Long id) {
        enableDisable(id, true);
    }

    public void enable(Long id) {
        enableDisable(id, false);
    }

    private void enableDisable(Long id, boolean b) {
        Account one = accountRepository.getOne(id);
        one.setDisabled(b);
        accountRepository.save(one);
    }
}
