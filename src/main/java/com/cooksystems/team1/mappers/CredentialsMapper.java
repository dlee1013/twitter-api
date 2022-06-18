package com.cooksystems.team1.mappers;

import com.cooksystems.team1.dtos.CredentialsDto;
import com.cooksystems.team1.entities.Credentials;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CredentialsMapper {
    CredentialsDto entityToDto(Credentials entity);

    List<CredentialsDto> entitiesToDtos(List<Credentials> entities);

    Credentials dtoToEntity(CredentialsDto credentialsDto);
}
