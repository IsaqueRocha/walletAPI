package com.wallet.repository;

import com.wallet.entity.WalletItem;
import com.wallet.util.enums.TypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface WalletItemRepository extends JpaRepository<WalletItem, Long> {

    Page<WalletItem> findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(Long wallet, Date init, Date end, Pageable pg);

    List<WalletItem> findByWalletIdAndType(Long wallet, TypeEnum typeEnum);

    @Query(value = "SELECT SUM(value) FROM WalletItem wi WHERE wi.wallet.id = :wallet")
    BigDecimal sumByWalletId(@Param( "wallet" ) Long wallet);
}
