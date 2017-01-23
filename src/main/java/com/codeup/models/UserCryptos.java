package com.codeup.models;

import org.springframework.data.repository.CrudRepository;

public interface UserCryptos extends CrudRepository<UserCrypto, Long> {
    UserCrypto findByPlayerIdAndCryptoId(long playerId, long cryptoId);
}
