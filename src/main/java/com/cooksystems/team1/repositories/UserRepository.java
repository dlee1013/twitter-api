package com.cooksystems.team1.repositories;

import com.cooksystems.team1.entities.Credentials;
import com.cooksystems.team1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByDeletedFalse();

    Optional<User> findById(Long id);


    List<User> findByCredentialsUsername(String username);
}
