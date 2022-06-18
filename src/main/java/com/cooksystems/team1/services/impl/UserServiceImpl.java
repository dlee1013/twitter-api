package com.cooksystems.team1.services.impl;

import com.cooksystems.team1.dtos.TweetResponseDto;
import com.cooksystems.team1.dtos.UserRequestDto;
import com.cooksystems.team1.dtos.UserResponseDto;
import com.cooksystems.team1.entities.Tweet;
import com.cooksystems.team1.entities.User;
import com.cooksystems.team1.exceptions.BadRequestException;
import com.cooksystems.team1.exceptions.NotAuthorizedException;
import com.cooksystems.team1.exceptions.NotFoundException;
import com.cooksystems.team1.mappers.TweetMapper;
import com.cooksystems.team1.mappers.UserMapper;
import com.cooksystems.team1.repositories.HashtagRepository;
import com.cooksystems.team1.repositories.TweetRepository;
import com.cooksystems.team1.repositories.UserRepository;
import com.cooksystems.team1.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private TweetRepository tweetRepository;
    private TweetMapper tweetMapper;
    private HashtagRepository hashtagRepository;


    @Override
    public List<UserResponseDto> getAllUsers() {

        return userMapper.entitiesToDtos(userRepository.findAll());
    }


    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User newUser = userMapper.dtoToEntity(userRequestDto);
        userRepository.saveAndFlush(newUser);
        if (newUser == null) {
            throw new NotAuthorizedException("You must use valid credentials");
        } else {
            return userMapper.entityToDto(newUser);
        }
    }

    @Override
    public UserResponseDto deleteUser(Long id) {
        Optional<User> toDelete = userRepository.findById(id);
        if (toDelete.isPresent()) {
            User user = toDelete.get();
            userRepository.delete(user);
            return userMapper.entityToDto(user);
        } else {
            throw new NotFoundException("Username not found in database");
        }
    }

    @Override

    public UserResponseDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("No tweet found with id: " + id);
        }
        return userMapper.entityToDto(user.get());
    }

    public List<UserResponseDto> getUserFollowing(String username) {
        List<User> incomingUser = userRepository.findByCredentialsUsername(username);
        if (incomingUser.isEmpty()) {
            throw new BadRequestException("User not found with username: " + username);
        }
        User user = incomingUser.get(0);
        List<User> following = user.getFollowing();

        for (User u : following) {
            if (u.isDeleted() || u == null) {
                following.remove(u);
            } else {
                throw new BadRequestException("User does not exist");
            }
        }
        return userMapper.entitiesToDtos(following);
    }

    @Override
    public List<UserResponseDto> getUserFollowers(String username) {
        List<User> incomingUser = userRepository.findByCredentialsUsername(username);
        if (incomingUser.isEmpty()) {
            throw new BadRequestException("User not found with username: " + username);
        }
        User user = incomingUser.get(0);
        List<User> followers = user.getFollowers();

        for (User u : followers) {
            if (u.isDeleted() || u == null) {
                followers.remove(u);
            } else {
                throw new BadRequestException("User does not exist");
            }
        }
        return userMapper.entitiesToDtos(followers);
    }


    @Override
    public List<TweetResponseDto> getUserMentions(String username) {
        List<User> incomingUser = userRepository.findByCredentialsUsername(username);
        if (incomingUser.isEmpty()) {
            throw new BadRequestException("User not found with username: " + username);
        }
        User user = incomingUser.get(0);
        List<Tweet> mentionedTweets = user.getTweetsMentioned();

        for (Tweet tweet : mentionedTweets) {
            if (tweet.isDeleted()) {
                mentionedTweets.remove(tweet);
            }
        }

        return tweetMapper.entitiesToDtos(mentionedTweets);
    }

    @Override
    public List<TweetResponseDto> getUserTweets(String username) {
        List<User> incomingUser = userRepository.findByCredentialsUsername(username);
        if (incomingUser.isEmpty()) {
            throw new BadRequestException("User not found with username: " + username);
        }
        User user = incomingUser.get(0);
        List<Tweet> tweets = user.getTweets();

        for (Tweet tweet : tweets) {
            if (tweet.isDeleted()) {
                tweets.remove(tweet);
            }
        }

        return tweetMapper.entitiesToDtos(tweets);
    }

    @Override
    public List<TweetResponseDto> getFeed(String username) {
        List<User> incomingUser = userRepository.findByCredentialsUsername(username);
        if (incomingUser.isEmpty()) {
            throw new BadRequestException("User not found with username: " + username);
        }
        User user = incomingUser.get(0);
        List<User> followingUsers = user.getFollowing();
        List<Tweet> userTweets = user.getTweets();

        for (User u : followingUsers) {
            userTweets.addAll(u.getTweets());
        }

        for (Tweet tweet : userTweets) {
            if (tweet.isDeleted()) {
                userTweets.remove(tweet);
            }
        }

        return tweetMapper.entitiesToDtos(userTweets);
    }


}



