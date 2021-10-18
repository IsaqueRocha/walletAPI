package com.wallet.repository;

import com.wallet.entity.Wallet;
import com.wallet.entity.WalletItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest
@ActiveProfiles("test")
class WalletItemRepositoryTests {
    private static final Date DATE = new Date();
    private static final String TYPE = "EN";
    private static final String DESCRIPTION = "Conta de luz";
    private static final BigDecimal VALUE = BigDecimal.valueOf( 65 );

    @Autowired
    WalletItemRepository walletItemRepository;
    @Autowired
    WalletRepository walletRepository;

    @Test
    void testSave() {
        Wallet w = new Wallet();
        w.setName( "Carteira 1" );
        w.setValue( BigDecimal.valueOf( 500 ) );
        walletRepository.save( w );

        WalletItem wi = new WalletItem( 1L, w, DATE, TYPE, DESCRIPTION, VALUE );
        WalletItem response = walletItemRepository.save( wi );

        Assertions.assertNotNull( response );
        Assertions.assertEquals( DATE, response.getDate() );
        Assertions.assertEquals( TYPE, response.getType() );
        Assertions.assertEquals( DESCRIPTION, response.getDescription() );
        Assertions.assertEquals( VALUE, response.getValue() );
        Assertions.assertEquals( w.getId(), response.getWallet().getId() );

    }
}
