package com.cooksystems.team1.services.impl;

import com.cooksystems.team1.dtos.ValidateDto;
import com.cooksystems.team1.entities.Hashtag;
import com.cooksystems.team1.entities.User;
import com.cooksystems.team1.entities.Validate;
import com.cooksystems.team1.exceptions.BadRequestException;
import com.cooksystems.team1.mappers.ValidateMapper;
import com.cooksystems.team1.repositories.HashtagRepository;
import com.cooksystems.team1.repositories.UserRepository;
import com.cooksystems.team1.services.ValidateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    private final UserRepository userRepository;

    private final ValidateMapper validateMapper;

    private final HashtagRepository hashtagRepository;


    @Override
    public ValidateDto validateUser(String username) {
        List<User> incomingUser = userRepository.findByCredentialsUsername(username);
        Validate validate = new Validate();

        if(incomingUser.isEmpty()){
            throw new BadRequestException("User not found with username: " + username);
        }
        if(incomingUser.get(0).equals(username)) {
            validate.setValid(true);
        }
        else{
            validate.setValid(false);
        }
        return validateMapper.entityToDto(validate);
    }

    @Override
    public ValidateDto validateTagExists(String label) {
        List<Hashtag> incomingHashtag = hashtagRepository.findByLabel(label);
        Validate validate = new Validate();

        if(incomingHashtag.get(0).equals(label)){
            validate.setValid(true);
        }else{
            validate.setValid(false);
        }
        return validateMapper.entityToDto(validate);
    }
}
