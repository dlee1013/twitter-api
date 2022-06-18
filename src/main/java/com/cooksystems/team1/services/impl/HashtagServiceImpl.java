package com.cooksystems.team1.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksystems.team1.dtos.HashtagDto;
import com.cooksystems.team1.mappers.HashtagMapper;
import com.cooksystems.team1.repositories.HashtagRepository;
import com.cooksystems.team1.services.HashtagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

	private final HashtagRepository hashtagRepository;
	
	private final HashtagMapper hashtagMapper;
	
	@Override
	public List<HashtagDto> getAllHashTags() {
		
		return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
	}
}
