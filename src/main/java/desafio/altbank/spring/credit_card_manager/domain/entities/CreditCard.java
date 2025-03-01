package desafio.altbank.spring.credit_card_manager.domain.entities;

import desafio.altbank.spring.credit_card_manager.domain.enums.CreditCardType;
import desafio.altbank.spring.credit_card_manager.domain.enums.CreditCardStatus;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "cvv", nullable = false, length = 3)
    private String cvv;
    @Column(name = "card_number", unique = true, nullable = false, length = 16)
    private String cardNumber;
    @Enumerated(EnumType.STRING)
    private CreditCardType type;
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
    @Column(name = "created_at", updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    @Column(name = "status", nullable = false)
    private CreditCardStatus status = CreditCardStatus.INACTIVE;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    @Column(name = "reissue_reason")
    private String reissueReason;

    @Column(name = "tracking_id", unique = true)
    private String trackingId;

    @Column(name = "delivery_status")
    private String deliveryStatus;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @Column(name = "delivery_return_reason")
    private String deliveryReturnReason;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "last_cvv_update")
    private LocalDateTime lastCvvUpdate;
}
