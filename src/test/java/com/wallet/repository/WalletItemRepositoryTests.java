package com.wallet.repository;

import com.wallet.entity.Wallet;
import com.wallet.entity.WalletItem;
import com.wallet.util.enums.TypeEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@SuppressWarnings("OptionalGetWithoutIsPresent")
class WalletItemRepositoryTests {
    private static final Date DATE = new Date();
    private static final TypeEnum TYPE = TypeEnum.EN;
    private static final String DESCRIPTION = "Conta de luz";
    private static final BigDecimal VALUE = BigDecimal.valueOf( 65 );
    private Long savedWalletItemId;
    private Long savedWalletId;

    @Autowired
    WalletItemRepository walletItemRepository;
    @Autowired
    WalletRepository walletRepository;

    @BeforeEach
    public void setUp() {
        Wallet w = new Wallet();
        w.setName( "Carteira Teste" );
        w.setValue( BigDecimal.valueOf( 250 ) );
        this.walletRepository.save( w );

        WalletItem wi = new WalletItem( null, w, DATE, TYPE, DESCRIPTION, VALUE );
        this.walletItemRepository.save( wi );

        this.savedWalletItemId = wi.getId();
        this.savedWalletId = w.getId();
    }

    @AfterEach
    public void tearDown() {
        walletItemRepository.deleteAll();
        walletRepository.deleteAll();
    }

    @Test
    void testSave() {

        Optional<Wallet> wallet = walletRepository.findById( this.savedWalletId );

        Wallet w = wallet.get();

        WalletItem wi = new WalletItem( 1L, w, DATE, TYPE, DESCRIPTION, VALUE );
        WalletItem response = walletItemRepository.save( wi );

        Assertions.assertNotNull( response );
        Assertions.assertEquals( DATE, response.getDate() );
        Assertions.assertEquals( TYPE, response.getType() );
        Assertions.assertEquals( DESCRIPTION, response.getDescription() );
        Assertions.assertEquals( VALUE, response.getValue() );
        Assertions.assertEquals( w.getId(), response.getWallet().getId() );
    }

    @Test
    void testUpdate() {
        String description = "Descrição alterada";

        Optional<WalletItem> wi = walletItemRepository.findById( this.savedWalletItemId );

        WalletItem changed = wi.get();
        changed.setDescription( description );

        walletItemRepository.save( changed );

        Optional<WalletItem> response = walletItemRepository.findById( this.savedWalletItemId );

        Assertions.assertTrue( response.isPresent() );
        Assertions.assertEquals( description, response.get().getDescription() );

    }

    @Test
    void testDelete() {
        Optional<Wallet> wallet = walletRepository.findById( this.savedWalletId );

        WalletItem wi = new WalletItem( null, wallet.get(), DATE, TYPE, DESCRIPTION, VALUE );

        walletItemRepository.save( wi );
        walletItemRepository.deleteById( wi.getId() );

        Optional<WalletItem> response = walletItemRepository.findById( wi.getId() );

        Assertions.assertFalse( response.isPresent() );
    }

    @Test
    void testSaveInvalidWalletItem() {
        WalletItem wi = new WalletItem( null, null, DATE, null, DESCRIPTION, null );

        Assertions.assertThrows( ConstraintViolationException.class, () -> {
            walletItemRepository.save( wi );
        } );
    }

    @Test
    void testFindBetweenDates() {
        Optional<Wallet> w = walletRepository.findById( this.savedWalletId );

        LocalDateTime localDateTime = DATE.toInstant().atZone( ZoneId.systemDefault() ).toLocalDateTime();

        Date currentDatePlusFiveDays = Date.from( localDateTime.plusDays( 5 )
                .atZone( ZoneId.systemDefault() )
                .toInstant() );
        Date currentDatePlusSevenDays = Date.from( localDateTime.plusDays( 7 )
                .atZone( ZoneId.systemDefault() )
                .toInstant() );

        walletItemRepository.save(
                new WalletItem( null, w.get(), currentDatePlusFiveDays, TYPE, DESCRIPTION, VALUE )
        );
        walletItemRepository.save(
                new WalletItem( null, w.get(), currentDatePlusSevenDays, TYPE, DESCRIPTION, VALUE )
        );

        Pageable pg = PageRequest.of( 0, 10 );

        Page<WalletItem> response = walletItemRepository
                .findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(
                        this.savedWalletId, currentDatePlusFiveDays, currentDatePlusSevenDays, pg
                );

        Assertions.assertEquals( 2, response.getContent().size() );
        Assertions.assertEquals( 2, response.getTotalElements() );
        Assertions.assertEquals( this.savedWalletId, response.getContent().get( 0 ).getWallet().getId() );
    }

    @Test
    void testFindByType() {
        List<WalletItem> response = walletItemRepository.findByWalletIdAndType( savedWalletId, TYPE );

        Assertions.assertEquals( 1, response.size() );
        Assertions.assertEquals( TYPE, response.get( 0 ).getType() );
    }

    @Test
    void testFindByTypeSd() {
        Optional<Wallet> w = walletRepository.findById( this.savedWalletId );

        walletItemRepository.save( new WalletItem( null, w.get(), DATE, TypeEnum.SD, DESCRIPTION, VALUE ) );

        List<WalletItem> response = walletItemRepository.findByWalletIdAndType( savedWalletId, TypeEnum.SD );

        Assertions.assertEquals( 1, response.size() );
        Assertions.assertEquals( TypeEnum.SD, response.get( 0 ).getType() );
    }

    @Test
    void testSumByWallet() {
        Optional<Wallet> w = walletRepository.findById( this.savedWalletId );

        walletItemRepository.save(
                new WalletItem( null, w.get(), DATE, TypeEnum.SD, DESCRIPTION, BigDecimal.valueOf( 150.80 ) )
        );

        BigDecimal response = walletItemRepository.sumByWalletId( this.savedWalletId );

        Assertions.assertEquals( 0, response.compareTo( BigDecimal.valueOf( 215.8 ) ) );
    }
}
