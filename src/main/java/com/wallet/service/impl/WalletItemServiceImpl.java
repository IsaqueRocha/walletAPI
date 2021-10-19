package com.wallet.service.impl;

import com.wallet.entity.WalletItem;
import com.wallet.repository.WalletItemRepository;
import com.wallet.service.WalletItemService;
import com.wallet.util.enums.TypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class WalletItemServiceImpl implements WalletItemService {
    @Autowired
    WalletItemRepository walletItemRepository;

    @Value("${pagination.items_per_page}")
    int itemsPerPage;

    @Override
    public WalletItem save(WalletItem i) {
        return walletItemRepository.save( i );
    }

    @Override
    public Page<WalletItem> findBetweenDates(Long wallet, Date start, Date end, int page) {
        Pageable pg = PageRequest.of( page, itemsPerPage );
        return walletItemRepository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual( wallet, start, end, pg );
    }

    @Override
    public List<WalletItem> findByWalletAndType(Long wallet, TypeEnum typeEnum) {
        return walletItemRepository.findByWalletIdAndType( wallet, typeEnum );
    }

    @Override
    public BigDecimal sumByWalletId(Long wallet) {
        return walletItemRepository.sumByWalletId( wallet );
    }
}
