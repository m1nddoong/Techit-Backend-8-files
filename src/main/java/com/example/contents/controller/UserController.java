package com.example.contents.controller;

import com.example.contents.dto.ErrorDto;
import com.example.contents.dto.UserDto;
import com.example.contents.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping
    public UserDto create(
            @RequestBody
            UserDto dto
    ) {
        return service.create(dto);
    }

    @GetMapping("/{username}")
    public UserDto read(
            @PathVariable("username")
            String username
    ) {
        return service.readByUsername(username);
    }

    @PutMapping("/{userId}/avatar")
    public UserDto avatar(
            @PathVariable("userId")
            Long userId,
            @RequestParam("image")
            MultipartFile imageFile
    ) {
        return service.updateUserAvatar(userId, imageFile);
    }


    // 컨트롤러 단위애서 예외처리를 하고싶은 경우
    /*@ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDto handleIllegalArgument(
            final IllegalArgumentException exception
    ) {
        log.warn(exception.getMessage());
        ErrorDto dto = new ErrorDto();
        dto.setMessage(exception.getMessage());
        return dto;
    }*/

}
