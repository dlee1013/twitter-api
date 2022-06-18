package com.cooksystems.team1.services;

import com.cooksystems.team1.dtos.ValidateDto;

public interface ValidateService {
    ValidateDto validateUser(String username);

    ValidateDto validateTagExists(String label);
}
