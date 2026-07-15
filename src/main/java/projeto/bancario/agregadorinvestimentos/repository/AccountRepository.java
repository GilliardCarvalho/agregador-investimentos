package projeto.bancario.agregadorinvestimentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projeto.bancario.agregadorinvestimentos.entity.Account;
import projeto.bancario.agregadorinvestimentos.entity.User;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
}
