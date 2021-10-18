package com.wallet.service.impl;

import com.wallet.entity.UserWallet;
import com.wallet.repository.UserWalletRepository;
import com.wallet.service.UserWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserWalletServiceImpl implements UserWalletService {
    @Autowired
    UserWalletRepository userWalletRepository;

    @Override
    public UserWallet save(UserWallet uw) {
        return userWalletRepository.save( uw );
    }
}
