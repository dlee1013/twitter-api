package com.cooksystems.team1.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Tweet {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User author;

    @Column(updatable = false)
    private Timestamp posted = Timestamp.valueOf(LocalDateTime.now());

    private boolean deleted = false;

    private String content;

    @ManyToOne
    private Tweet inReplyTo;

    @OneToMany(mappedBy = "inReplyTo", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Tweet> replies;

    @ManyToOne
    private Tweet repostOf;

    @OneToMany(mappedBy = "repostOf", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Tweet> reposts;

    @ManyToMany(mappedBy = "likedTweets")
    private List<User> likes;

    @ManyToMany
    @JoinTable(name="tweet_hashtags")
    private List<Hashtag> hashtags;

    @ManyToMany
    private List<User> usersMentioned;
}
