package com.codeup.auth;

import com.codeup.models.Crypto;
import com.codeup.models.UserCrypto;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Must not be blank")
    @Size(min = 5, max = 40, message = "Must be 5-40 characters")
    @Column(nullable = false, unique = true)
    private String username;

    @Email(message = "Must be a valid email")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private long points;

    @Column(nullable = false, name = "admin")
    private boolean admin;

    @OneToMany(mappedBy = "user")
    private List<Crypto> cryptos;

    @OneToMany(mappedBy = "player")
    private List<UserCrypto> userCryptos;

    public User() {
    }

    public User(User user) {
        id = user.id;
        email = user.email;
        username = user.username;
        password = user.password;
    }

    public List<Crypto> getCryptos(){
        return cryptos;
    }

    public List<Crypto> getOrderedCryptos(){
        List<Crypto> orderedCryptoList = new ArrayList<>();
        for (int i = cryptos.size()-1; i >= 0; i--) {
            orderedCryptoList.add(cryptos.get(i));
        }
        return orderedCryptoList;
    }

    public long getId(){ return id;}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail(){return email;}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<UserCrypto> getUserCryptos() {
        return userCryptos;
    }

    public void setUserCryptos(List<UserCrypto> userCryptos) {
        this.userCryptos = userCryptos;
    }

    @Override
    public String toString(){
        return "Username " + getUsername() + " Email " + getEmail() + " Admin " + getAdmin() + " Points " + getPoints();
    }
}
