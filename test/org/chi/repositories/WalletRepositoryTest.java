package org.chi.repositories;

import org.chi.models.Wallet;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class WalletRepositoryTest {

    private final WalletRepository  walletRepository = new WalletRepository();
    @Test
     void testSave(){
        Wallet wallet = new Wallet();
        wallet.setBalance(new BigDecimal(1000));
        wallet = walletRepository.save(wallet);
        assertNotNull(wallet);
        assertEquals(new BigDecimal(1000), wallet.getBalance());

    }

    @Test
    void testFindById(){
        Optional<Wallet>foundWallet = walletRepository.findById(1L);
        assertTrue(foundWallet.isPresent());
    }

}