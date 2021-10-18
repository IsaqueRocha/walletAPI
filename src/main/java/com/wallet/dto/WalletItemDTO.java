package com.wallet.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class WalletItemDTO {
    private Long id;
    @NotNull(message = "Adicione o id de uma carteira.")
    private Long wallet;
    @NotNull(message = "Informe uma data.")
    private Date date;
    @NotNull(message = "Informe um tipo.")
    @Pattern( regexp = "^(ENTRADA|SAÍDA)$", message = "Para o tipo são aceitos somente ENTRADA ou SAÍDA.")
    private String type;
    @NotNull(message = "Informe uma descrição.")
    @Size(min = 5, message = "A descrição deve ter no mínimo 5 caracteres.")
    private String description;
    @NotNull(message = "Informe um valor.")
    private BigDecimal value;
}
