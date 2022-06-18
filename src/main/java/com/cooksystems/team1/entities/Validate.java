package com.cooksystems.team1.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
public class Validate {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private boolean valid;
}
