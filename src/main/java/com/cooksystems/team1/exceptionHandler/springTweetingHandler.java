package com.cooksystems.team1.exceptionHandler;

import javax.servlet.http.HttpServletRequest;

import com.cooksystems.team1.dtos.ErrorDto;
import com.cooksystems.team1.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cooksystems.team1.exceptions.NotAuthorizedException;
import com.cooksystems.team1.exceptions.NotFoundException;

@ControllerAdvice(basePackages = { "com.cooksystems.team1.Controller" })
@ResponseBody
public class springTweetingHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler( value = {NotFoundException.class})
    public ErrorDto handleNotFoundException(HttpServletRequest request, NotFoundException notFoundException) {
        return new ErrorDto(notFoundException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler( value = {BadRequestException.class})
    public ErrorDto handleBadRequestException(HttpServletRequest request, BadRequestException badRequestException) {
        return new ErrorDto(badRequestException.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler( value = {NotAuthorizedException.class})
    public ErrorDto handleNotAuthorizedException(HttpServletRequest request, NotAuthorizedException notAuthorizedException) {
        return new ErrorDto(notAuthorizedException.getMessage());
    }


}
