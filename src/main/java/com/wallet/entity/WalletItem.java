package com.wallet.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "wallet_items")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WalletItem implements Serializable {
    private static final long serialVersionUID = -8323354823496579099L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "wallet", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Wallet wallet;
    @NotNull
    private Date date;
    @NotNull
    private String type;
    @NotNull
    private String description;
    @NotNull
    private BigDecimal value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass( this ) != Hibernate.getClass( o )) return false;
        WalletItem that = (WalletItem) o;
        return id != null && Objects.equals( id, that.id );
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
