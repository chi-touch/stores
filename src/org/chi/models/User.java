package org.chi.models;

public class User {

    private Long id;
    private Long walletId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", walletId=" + walletId +
                '}';
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long wallet) {
        this.walletId = wallet;
    }



}
