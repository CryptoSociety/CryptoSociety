package com.codeup.models;

import com.codeup.auth.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Crypto")
public class Crypto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User user;

    @NotBlank(message = "Name can't be empty")
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private long points;

    @Column(nullable = false, name = "crypto_text", columnDefinition="TEXT")
    private String cryptoText;

    @NotBlank(message = "Plaintext can't be empty")
    @Column(nullable = false, name = "plain_text", columnDefinition="TEXT")
    private String plainText;

    @NotBlank(message = "Solution can't be empty")
    @Column(nullable = false, columnDefinition="TEXT")
    private String solution;

    @NotBlank(message = "A scheme must be selected")
    @Column(nullable = false)
    private String scheme;

    @Column(nullable = false)
    private String cryptokey;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created")
    private Date creationDate;

    @Column(nullable = false, name = "users_solved")
    private long usersSolved;

    @Column(nullable = false, name = "is_approved")
    private boolean isApproved;

    @Column(nullable = false, name = "active")
    private boolean active;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "crypto")
    private List<UserCrypto> userCryptos;


    public Crypto(String name, long points, String cryptoText, String plainText, String solution, String scheme, String cryptokey, boolean isApproved){
        this.name = name;
        this.points = points;
        this.cryptoText = cryptoText;
        this.plainText = plainText;
        this.solution = solution;
        this.scheme = scheme;
        this.cryptokey = cryptokey;
        this.usersSolved = 0;
        this.isApproved = isApproved;
    }

    public Crypto(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public String getCryptoText() {
        return cryptoText;
    }

    public void setCryptoText(String cryptoText) {
        this.cryptoText = cryptoText;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getCryptokey() {
        return cryptokey;
    }

    public void setCryptokey(String cryptokey) {
        this.cryptokey = cryptokey;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public long getUsersSolved() {
        return usersSolved;
    }

    public void setUsersSolved(long usersSolved) {
        this.usersSolved = usersSolved;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

