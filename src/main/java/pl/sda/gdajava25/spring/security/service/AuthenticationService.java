package pl.sda.gdajava25.spring.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.sda.gdajava25.spring.security.model.Account;
import pl.sda.gdajava25.spring.security.model.AccountRole;
import pl.sda.gdajava25.spring.security.repository.AccountRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findByUsername(s);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();

            String[] roles = account.getAccountRoles()
                    .stream()
                    .map(AccountRole::getName).toArray(String[]::new);

            return User.builder()
                    .username(account.getUsername())
                    .password(account.getPassword())
                    .roles(roles)
                    .disabled(account.isDisabled())
                    .build();
        }
        throw new UsernameNotFoundException("Username not found.");
    }
}
