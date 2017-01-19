package com.codeup.models;

import org.springframework.data.repository.CrudRepository;

public interface UserCryptos extends CrudRepository<UserCrypto, Long> {
    public UserCrypto findByPlayerIdAndCryptoId(long playerId, long cryptoId);
}
