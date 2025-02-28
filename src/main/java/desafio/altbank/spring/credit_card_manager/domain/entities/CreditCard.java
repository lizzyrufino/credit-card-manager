package desafio.altbank.spring.credit_card_manager.domain.entities;

import desafio.altbank.spring.credit_card_manager.domain.enums.CardType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "credit_card")
@AllArgsConstructor
public class CreditCard {
    @Id //chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //cria um id novo toda vez
    private Long id;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "cvv", nullable = false, length = 3)
    private String cvv;
    @Column(name = "card_number", unique = true, nullable = false, length = 16) //a gente coloca isso quando o nome da coluna ta diferente da string
    private String cardNumber;
    @Enumerated(EnumType.STRING) //indica para o banco usar o valor de string para esse enum
    private CardType type;
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
    @Column(name = "created_at", updatable = false) //qual coluna se referencia e essa coluna nunca pode ter o valor alterado.
    @Builder.Default //so onde recebe valor
    private LocalDateTime createdAt = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false) //quando eu chamar o credit card, ele automaticamente vincula a conta que esta atrelada.
    private Account account;
}
