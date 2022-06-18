package com.cooksystems.team1.mappers;

import com.cooksystems.team1.dtos.TweetRequestDto;
import com.cooksystems.team1.dtos.TweetResponseDto;
import com.cooksystems.team1.entities.Tweet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface TweetMapper {
    TweetResponseDto entityToDto(Tweet entity);

    List<TweetResponseDto> entitiesToDtos(List<Tweet> entities);

    Tweet dtoToEntity(TweetRequestDto tweetRequestDto);

}
