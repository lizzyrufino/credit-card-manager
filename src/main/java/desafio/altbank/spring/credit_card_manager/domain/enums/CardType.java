package desafio.altbank.spring.credit_card_manager.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CardType {

    CARD("CARD"),
    VIRTUAL_CARD("VIRTUAL_CARD");
    private final String value;
}
