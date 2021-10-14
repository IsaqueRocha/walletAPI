package com.wallet.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "users_wallet")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserWallet implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "users", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User users;
    @JoinColumn(name = "wallet", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Wallet wallet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass( this ) != Hibernate.getClass( o )) return false;
        UserWallet that = (UserWallet) o;
        return id != null && Objects.equals( id, that.id );
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
