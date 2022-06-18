package com.cooksystems.team1.services.impl;

import com.cooksystems.team1.dtos.*;
import com.cooksystems.team1.entities.Credentials;
import com.cooksystems.team1.entities.Hashtag;
import com.cooksystems.team1.entities.Tweet;
import com.cooksystems.team1.entities.User;
import com.cooksystems.team1.exceptions.BadRequestException;
import com.cooksystems.team1.exceptions.NotAuthorizedException;
import com.cooksystems.team1.exceptions.NotFoundException;
import com.cooksystems.team1.mappers.CredentialsMapper;
import com.cooksystems.team1.mappers.HashtagMapper;
import com.cooksystems.team1.mappers.TweetMapper;
import com.cooksystems.team1.mappers.UserMapper;
import com.cooksystems.team1.repositories.TweetRepository;
import com.cooksystems.team1.repositories.UserRepository;
import com.cooksystems.team1.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;

    private final TweetMapper tweetMapper;

    private final CredentialsMapper credentialsMapper;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final HashtagMapper hashtagMapper;

    @Override
    public List<TweetResponseDto> getAllTweets() {
        return tweetMapper.entitiesToDtos(tweetRepository.findAll());
    }

    @Override
    public TweetResponseDto getTweetById(Long id) {
        Optional<Tweet> tweet = tweetRepository.findById(id);

        if (tweet.isEmpty()) {
            throw new NotFoundException("No tweet found with id: " + id);
        }
        return tweetMapper.entityToDto(tweet.get());
    }

    @Override
    public TweetResponseDto deleteTweet(Long id) {
        Optional<Tweet> tweetToDelete = tweetRepository.findById(id);

        if (tweetToDelete.isPresent()) {
            Tweet tweetWeAreDeleting = tweetToDelete.get();
            tweetWeAreDeleting.setDeleted(true);
            tweetRepository.delete(tweetWeAreDeleting);
            return tweetMapper.entityToDto(tweetWeAreDeleting);
        } else {
            throw new NotFoundException("No tweet found with id: " + id);
        }
    }

    @Override
    public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
        if (tweetRequestDto.getContent() == null) {
            throw new BadRequestException("Content field is required for creating a tweet");
        }
        Tweet incomingTweet = tweetMapper.dtoToEntity(tweetRequestDto);
        Credentials incomingCredentials = credentialsMapper.dtoToEntity(tweetRequestDto.getCredentials());

        List<User> findTheUser = userRepository.findByCredentialsUsername(incomingCredentials.getUsername());
        if (findTheUser == null) {
            throw new NotFoundException("No user found");
        }
        incomingTweet.setAuthor(findTheUser.get(0));

        Tweet savedTweet = tweetRepository.saveAndFlush(incomingTweet);
        return tweetMapper.entityToDto(savedTweet);

    }

    @Override
    public void likeTweet(Long id, CredentialsDto credentialsDto) {
        Optional<Tweet> optionalTweet = tweetRepository.findById(id);
        Credentials incomingCredentials = credentialsMapper.dtoToEntity(credentialsDto);

        List<User> findTheUser = userRepository.findByCredentialsUsername(incomingCredentials.getUsername());
        if (findTheUser == null) {
            throw new NotFoundException("No user found");
        }
        if (optionalTweet.isEmpty()) {
            throw new NotFoundException("No tweet found with id: " + id);
        }
        Tweet likedTweet = optionalTweet.get();
        if (likedTweet.isDeleted()) {
            throw new BadRequestException("Tweet has been deleted with id: " + id);
        }

        List<User> usersWhoLiked = likedTweet.getLikes();
        usersWhoLiked.add(findTheUser.get(0));

        List<Tweet> likedTweetList = findTheUser.get(0).getLikedTweets();
        likedTweetList.add(likedTweet);
        findTheUser.get(0).setLikedTweets(likedTweetList);

        Tweet updatedTweet = tweetRepository.saveAndFlush(likedTweet);
        tweetMapper.entityToDto(updatedTweet);

    }

    @Override
    public TweetResponseDto replyToTweet(Long id, TweetRequestDto tweetRequestDto) {
        if (tweetRequestDto.getContent() == null) {
            throw new BadRequestException("Content field is required for creating a tweet");
        }

        Optional<Tweet> optionalTweet = tweetRepository.findById(id);
        Tweet incomingTweet = tweetMapper.dtoToEntity(tweetRequestDto);
        Credentials incomingCredentials = credentialsMapper.dtoToEntity(tweetRequestDto.getCredentials());

        List<User> findTheUser = userRepository.findByCredentialsUsername(incomingCredentials.getUsername());
        if (findTheUser == null) {
            throw new NotFoundException("No user found");
        }
        if (optionalTweet.isEmpty()) {
            throw new NotFoundException("No tweet found with id: " + id);
        }
        incomingTweet.setAuthor(findTheUser.get(0));
        Tweet tweetToReplyTo = optionalTweet.get();
        incomingTweet.setInReplyTo(tweetToReplyTo);

        Tweet savedTweet = tweetRepository.saveAndFlush(incomingTweet);
        return tweetMapper.entityToDto(savedTweet);
    }

    @Override
    public List<UserResponseDto> getUsersMentionedInTweet(Long id) {
        Optional<Tweet> optionalTweet = tweetRepository.findById(id);

        if (optionalTweet.isEmpty()) {
            throw new NotFoundException("No tweet found with id: " + id);
        }

        Tweet tweet = optionalTweet.get();
        List<User> mentionedUsers = tweet.getUsersMentioned();

        for (User user : mentionedUsers) {
            if (user.isDeleted() || user == null) {
                mentionedUsers.remove(user);
            }
        }
        return userMapper.entitiesToDtos(mentionedUsers);

    }

    @Override
    public List<HashtagDto> getHashtagsByTweet(Long id) {
        Optional<Tweet> tweetToFind = tweetRepository.findById(id);

        if (tweetToFind.isEmpty()) {
            throw new NotFoundException("Tweet not found by getHashtagsByTweet(id)");
        }
        Tweet foundTweet = tweetToFind.get();
        Optional<List<Hashtag>> hashtagsToFind = Optional.ofNullable(foundTweet.getHashtags());

        if (hashtagsToFind.isEmpty()) {
            throw new NotFoundException("Tweet in getHashTagsByTweet(id) has no hashtags.");
        }
        List<Hashtag> hashtagsToReturn = hashtagsToFind.get();
        return hashtagMapper.entitiesToDtos(hashtagsToReturn);

    }

    @Override
    public List<UserResponseDto> getActiveUsersThatLikedTweet(Long id) {
        Optional<Tweet> tweetToFind = tweetRepository.findById(id);

        if (tweetToFind.isPresent()) {
            Tweet foundTweet = tweetToFind.get();

            if (foundTweet.isDeleted()) throw new NotFoundException("Tweet may have been deleted.");

            ArrayList<UserResponseDto> activeUsersThatLiked = new ArrayList<>();

            for (User u : foundTweet.getLikes()) {
                if (!u.isDeleted()) {
                    activeUsersThatLiked.add(userMapper.entityToDto(u));
                }
            }
            return activeUsersThatLiked;

        } else {
            throw new NotFoundException("tweet not found by getActiveUsersThatLikedTweet()");
        }
    }

    @Override
    public TweetResponseDto createRepost(Long id, CredentialsDto credentialsDto) {
//        May want to add repository method that returns single user, username is unique in the database
        List<User> requestingUser = userRepository.findByCredentialsUsername(credentialsDto.getUsername());
        Optional<Tweet> tweetToFind = tweetRepository.findById(id);

//        Validate tweet
        if (tweetToFind.isEmpty()) throw new NotFoundException("Could not find a tweet with id: " + id);

//        Validate deleted
        Tweet tweetToRepost = tweetToFind.get();
        if (tweetToRepost.isDeleted()) throw new BadRequestException("Cannot repost deleted tweet with id: " + id );

//        Validate + Authorize user
        if (requestingUser.isEmpty()) throw new NotAuthorizedException("Unable to find user");
        if (!credentialsDto.getPassword().equals(requestingUser.get(0).getCredentials().getPassword())){
            throw new NotAuthorizedException("Unable to authorize user.");}

        Tweet repost = new Tweet();
        repost.setAuthor(requestingUser.get(0));
        repost.setRepostOf(tweetToRepost);

        return tweetMapper.entityToDto(tweetRepository.saveAndFlush(repost));

        //  find tweet by id, create tweet + set attributes, author of repost should match credentials
        //  content not allowed in reposts
        //  repostOf attribute must be set with logic
        //  if given tweet is deleted || doesn't exist return not found
        //  if credentials do not match and active user return not authorized

    }
}