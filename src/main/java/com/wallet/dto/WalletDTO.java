package com.wallet.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class WalletDTO {

    private Long id;
    @Size(min = 3, message = "O nome deve conter no mínimo 3 caracteres.")
    @NotNull(message = "O nome não deve ser nulo.")
    private String name;
    @NotNull(message = "Insira um valor para a carteira.")
    private BigDecimal value;
}
