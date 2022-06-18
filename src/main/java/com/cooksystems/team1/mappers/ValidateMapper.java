package com.cooksystems.team1.mappers;

import com.cooksystems.team1.dtos.ValidateDto;
import com.cooksystems.team1.entities.Validate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ValidateMapper {

    ValidateDto entityToDto(Validate entity);

    Validate dtoToEntity(ValidateDto validateDto);

}
