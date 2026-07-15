package projeto.bancario.agregadorinvestimentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projeto.bancario.agregadorinvestimentos.entity.AccountStock;
import projeto.bancario.agregadorinvestimentos.entity.AccountStockId;
import projeto.bancario.agregadorinvestimentos.entity.User;

import java.util.UUID;

@Repository
public interface AccountStockRepository
        extends JpaRepository<AccountStock, AccountStockId> {
}
