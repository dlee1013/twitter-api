package com.cooksystems.team1.services;

import java.util.List;

import com.cooksystems.team1.dtos.TweetResponseDto;
import com.cooksystems.team1.dtos.UserRequestDto;
import com.cooksystems.team1.dtos.UserResponseDto;
import com.cooksystems.team1.entities.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    List<UserResponseDto> getAllUsers();

    UserResponseDto createUser(UserRequestDto userRequestDto);


    UserResponseDto deleteUser(Long id);

    Object getUserById(Long id);

    List<UserResponseDto> getUserFollowing(String username);

    List<UserResponseDto> getUserFollowers(String username);

    List<TweetResponseDto> getUserMentions(String username);

    List<TweetResponseDto> getUserTweets(String username);

    List<TweetResponseDto> getFeed(String username);
}
