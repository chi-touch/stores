package org.chi.service;

import org.chi.models.Wallet;
import org.chi.repositories.UserRepository;

import java.math.BigDecimal;

public class UserServices {

    private final UserRepository userRepository = new UserRepository();

    public String  transferFunds(Long senderId, Long receiptId, BigDecimal amount) {
        return null;
    }

    public Wallet getWalletById(Long id){
        return userRepository.findById(id)
                .orElseThrow();
    }
}
