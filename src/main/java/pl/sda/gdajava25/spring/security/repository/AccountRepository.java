package pl.sda.gdajava25.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.gdajava25.spring.security.model.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername(String username);

    boolean existsByUsername(String username);
}
