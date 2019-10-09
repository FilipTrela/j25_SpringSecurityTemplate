package pl.sda.gdajava25.spring.security.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.sda.gdajava25.spring.security.model.AccountRole;
import pl.sda.gdajava25.spring.security.repository.AccountRoleRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@AllArgsConstructor
@Service
public class AccountRoleServis {

    private final AccountRoleRepository accountRoleRepository;

    @Value("account.default.roles:USER")
    private String[] defaultRoles;

    public Set<AccountRole> getDefaultRoles(){
        Set<AccountRole> accountRoles = new HashSet<>();
        for (String role: defaultRoles){
            Optional<AccountRole> accountRoleOptional = accountRoleRepository.findByName(role);
            accountRoleOptional.ifPresent(accountRoles::add);
        }
        return accountRoles;
    }

}
