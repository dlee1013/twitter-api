package com.cooksystems.team1.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksystems.team1.dtos.HashtagDto;
import com.cooksystems.team1.entities.Hashtag;

@Mapper(componentModel = "spring")
public interface HashtagMapper {

    HashtagDto entityToDto(Hashtag entity);

    List<HashtagDto> entitiesToDtos(List<Hashtag> entities);

    Hashtag dtoToEntity(HashtagDto hashtagDto);

}
