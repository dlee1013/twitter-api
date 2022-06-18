package com.cooksystems.team1.mappers;

import com.cooksystems.team1.dtos.ProfileDto;
import com.cooksystems.team1.entities.Profile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ProfileMapper {
    ProfileDto entityToDto(Profile entity);

    List<ProfileDto> entitiesToDtos(List<Profile> entities);

    Profile dtoToEntity(ProfileDto profileDto);
}
