package com.wallet.service;

import com.wallet.entity.Wallet;
import com.wallet.entity.WalletItem;
import com.wallet.repository.WalletItemRepository;
import com.wallet.util.enums.TypeEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class WalletItemServiceTests {

    private static final Date DATE = new Date();
    private static final TypeEnum TYPE = TypeEnum.EN;
    private static final String DESCRIPTION = "Conta de Luz";
    private static final BigDecimal VALUE = BigDecimal.valueOf( 65 );

    @MockBean
    WalletItemRepository walletItemRepository;

    @Autowired
    WalletItemService walletItemService;

    @Test
    void testSave() {
        BDDMockito
                .given( walletItemRepository.save( Mockito.any( WalletItem.class ) ) )
                .willReturn( getMockWalletItem() );

        WalletItem response = walletItemService.save( new WalletItem() );

        Assertions.assertNotNull( response );
        Assertions.assertEquals( DESCRIPTION, response.getDescription() );
        Assertions.assertEquals( 0, response.getValue().compareTo( VALUE ) );
    }


    @Test
    void testFindBetweenDates() {
        List<WalletItem> list = new ArrayList<>();
        list.add( getMockWalletItem() );

        Page<WalletItem> page = new PageImpl<>( list );

        BDDMockito.given(
                walletItemRepository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(
                        Mockito.anyLong(),
                        Mockito.any( Date.class ),
                        Mockito.any( Date.class ),
                        Mockito.any( Pageable.class )
                )
        ).willReturn( page );

        Page<WalletItem> response = walletItemService.findBetweenDates( 1L, new Date(), new Date(), 0 );

        Assertions.assertNotNull( response );
        Assertions.assertEquals( 1, response.getContent().size() );
        Assertions.assertEquals( DESCRIPTION, response.getContent().get( 0 ).getDescription() );
    }

    @Test
    void testFindByType() {
        List<WalletItem> list = new ArrayList<>();
        list.add( getMockWalletItem() );

        BDDMockito
                .given( walletItemRepository.findByWalletIdAndType( Mockito.anyLong(), Mockito.any( TypeEnum.class ) ) )
                .willReturn( list );

        List<WalletItem> response = walletItemService.findByWalletAndType( 1L, TypeEnum.EN );

        Assertions.assertNotNull( response );
        Assertions.assertEquals( TYPE, response.get( 0 ).getType() );
    }

    @Test
    void testSumByWalletId() {
        BigDecimal value = BigDecimal.valueOf( 45 );

        BDDMockito.given( walletItemRepository.sumByWalletId( Mockito.anyLong() ) ).willReturn( value );

        BigDecimal response = walletItemService.sumByWalletId( 1L );

        Assertions.assertEquals( 0, response.compareTo( value ) );
    }

    private WalletItem getMockWalletItem() {
        Wallet w = new Wallet();
        w.setId( 1L );

        return new WalletItem( 1L, w, DATE, TYPE, DESCRIPTION, VALUE );
    }
}