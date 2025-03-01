package desafio.altbank.spring.credit_card_manager.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "account")
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //cria um id novo toda vez
    private Long id;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    @Column(name = "cpf", unique = true, nullable = false, length = 11)
    private String cpf;
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "created_at", updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    @Column(name = "status", nullable = false)
    private Boolean status = true;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<CreditCard> creditCards;
}
