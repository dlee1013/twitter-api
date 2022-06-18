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
public class Hashtag {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String label;

    @Column(nullable = false)
    private Timestamp firstUsed = Timestamp.valueOf(LocalDateTime.now());

    @Column(nullable = false)
    private Timestamp lastUsed = Timestamp.valueOf(LocalDateTime.now());

    @ManyToMany(mappedBy = "hashtags")
    private List<Tweet> tweets;
}
