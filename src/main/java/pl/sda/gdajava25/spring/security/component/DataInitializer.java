package pl.sda.gdajava25.spring.security.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.sda.gdajava25.spring.security.model.Account;
import pl.sda.gdajava25.spring.security.model.AccountRole;
import pl.sda.gdajava25.spring.security.repository.AccountRepository;
import pl.sda.gdajava25.spring.security.repository.AccountRoleRepository;

import java.util.HashSet;

@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private AccountRepository accountRepository;
    private AccountRoleRepository accountRoleRepository;
    private PasswordEncoder passwordEncoder;
    @Value("account.default.roles:USER")
    private String[] defaultRoles;

    @Autowired
    public DataInitializer(AccountRepository accountRepository, AccountRoleRepository accountRoleRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.accountRoleRepository = accountRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        addNewRole(defaultRoles);
        addDefaultRole("USER");
        addDefaultRole("ADMIN");
        addDefaultAccount("admin", "admin", "ADMIN", "USER");
        addDefaultAccount("user", "user", "USER");

    }

    private void addNewRole(String[] defaultRoles) {
        for (String role : defaultRoles){
            addDefaultRole(role);
        }
    }

    private void addDefaultAccount(String username, String password, String... roles) {
        if (!accountRepository.existsByUsername(username)) {
            Account account = new Account();
            account.setUsername(username);
            account.setPassword(passwordEncoder.encode(password));
            account.setAccountRoles(setRoles(roles));

            accountRepository.save(account);
        }
    }

    private HashSet<AccountRole> setRoles(String[] roles) {
        HashSet<AccountRole> accountRoles = new HashSet<>();
        for (String role : roles) {
            accountRoleRepository.findByName(role).ifPresent(accountRoles::add);
        }
        return accountRoles;
    }

    private void addDefaultRole(String name) {
        if (!accountRoleRepository.existsByName(name)) {
            AccountRole newRole = new AccountRole();
            newRole.setName(name);

            accountRoleRepository.save(newRole);
        }
    }
}
