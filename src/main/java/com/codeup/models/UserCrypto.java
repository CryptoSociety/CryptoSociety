package com.codeup.models;

import com.codeup.auth.User;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name="user_crypto")

public class UserCrypto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Crypto crypto;

    @ManyToOne
    private User player;

    public UserCrypto(){}

    public long getId() {
        return id;
    }

    public Crypto getCrypto() {
        return crypto;
    }

    public void setCrypto(Crypto crypto) {
        this.crypto = crypto;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

}
