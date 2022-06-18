package com.cooksystems.team1.services;

import com.cooksystems.team1.dtos.*;


import java.util.List;

public interface TweetService {

    List<TweetResponseDto> getAllTweets();

    TweetResponseDto getTweetById(Long id);

    TweetResponseDto deleteTweet(Long id);

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

    void likeTweet(Long id, CredentialsDto credentialsDto);

    TweetResponseDto replyToTweet(Long id, TweetRequestDto tweetRequestDto);

    List<UserResponseDto> getUsersMentionedInTweet(Long id);

    List<HashtagDto> getHashtagsByTweet(Long id);

    List<UserResponseDto> getActiveUsersThatLikedTweet(Long id);

    TweetResponseDto createRepost(Long id, CredentialsDto credentialsDto);
}
