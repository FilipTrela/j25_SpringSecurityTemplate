package pl.sda.gdajava25.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.gdajava25.spring.security.model.AccountRole;

import java.util.Optional;

@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole, Long> {

    Optional<AccountRole> findByName(String name);

    boolean existsByName(String name);

}
