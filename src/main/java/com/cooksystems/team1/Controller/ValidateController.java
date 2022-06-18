package com.cooksystems.team1.Controller;

import com.cooksystems.team1.dtos.TweetResponseDto;
import com.cooksystems.team1.dtos.ValidateDto;
import com.cooksystems.team1.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validate")
public class ValidateController {

    private final ValidateService validateService;

    @GetMapping("/username/available/@{username}")
    public ValidateDto validateUser(@PathVariable String username) {
        return validateService.validateUser(username);
    }

    @GetMapping("/tag/exists/{label}")
    public ValidateDto validateTagExists(@PathVariable String label){
        return validateService.validateTagExists(label);
    }
}
