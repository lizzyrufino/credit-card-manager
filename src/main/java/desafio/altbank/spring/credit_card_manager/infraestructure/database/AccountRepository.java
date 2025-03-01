package desafio.altbank.spring.credit_card_manager.infraestructure.database;

import desafio.altbank.spring.credit_card_manager.domain.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByCpf(String cpf);
    List<Account> findByCpf(String cpf);
}
