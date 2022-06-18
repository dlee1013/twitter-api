package com.cooksystems.team1.Controller;

import com.cooksystems.team1.dtos.CredentialsDto;
import com.cooksystems.team1.dtos.HashtagDto;
import com.cooksystems.team1.dtos.TweetRequestDto;
import com.cooksystems.team1.dtos.TweetResponseDto;
import com.cooksystems.team1.dtos.UserResponseDto;
import com.cooksystems.team1.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;

    @GetMapping
    public List<TweetResponseDto> getAllTweets() {
        return tweetService.getAllTweets();
    }

    @GetMapping("/{id}")
    public TweetResponseDto getTweetById(@PathVariable Long id) {
        return tweetService.getTweetById(id);
    }

    @GetMapping("/{id}/tags")
    public List<HashtagDto> getHashtagsByTweet(@PathVariable Long id) {
        return tweetService.getHashtagsByTweet(id);
    }

    @DeleteMapping("/{id}")
    public TweetResponseDto deleteTweet(@PathVariable Long id) {
        return tweetService.deleteTweet(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto) {
        return tweetService.createTweet(tweetRequestDto);
    }

    @PostMapping("/{id}/like")
    @ResponseStatus(HttpStatus.CREATED)
    public void likeTweet(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
        tweetService.likeTweet(id, credentialsDto);
    }

    @PostMapping("/{id}/reply")
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseDto replyToTweet(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequestDto) {
        return tweetService.replyToTweet(id, tweetRequestDto);
    }

    @GetMapping("/{id}/mentions")
    @ResponseStatus(HttpStatus.CREATED)
    public List<UserResponseDto> getUsersMentionedInTweet(@PathVariable Long id) {
        return tweetService.getUsersMentionedInTweet(id);
    }


    @GetMapping("/{id}/likes")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> getActiveUsersThatLikedTweet(@PathVariable Long id) {
        return tweetService.getActiveUsersThatLikedTweet(id);
    }

    @PostMapping("/{id}/repost")
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseDto createRepost(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
        return tweetService.createRepost(id, credentialsDto);
    }
}
