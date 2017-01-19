package com.codeup.models;

import com.github.rjeschke.txtmark.Processor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name="walkthrough")
public class Walkthrough {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Title can't be empty.")
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters.")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Body can't be empty.")
    @Column(nullable = false, columnDefinition="TEXT")
    private String body;

    @NotBlank(message = "A scheme must be selected.")
    @Column(nullable = false)
    private String scheme;

    @NotBlank(message = "Please select a difficulty ranking, 1 (easiest) to 100 (hardest)")
    private int difficulty;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created")
    private Date creationDate;

    public Walkthrough(){}

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getParsedBody() {
        return Processor.process(getBody());
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setDifficulty(int difficulty){this.difficulty = difficulty;}

    public int getDifficulty(){return difficulty;}
}
