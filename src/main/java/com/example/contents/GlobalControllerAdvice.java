package com.example.contents;

import com.example.contents.dto.ErrorDto;
import com.example.contents.exceptions.Status400Exception;
import com.example.contents.exceptions.UsernameExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


// Controller 는 아니고
// 예외 처리를 위한 ExceptionHadler 를 모아놓기 위한
// 컨트롤러에게 제시를 하기 위한 일종의 Bean 컴포넌트 객체
@RestControllerAdvice
public class GlobalControllerAdvice {
    // 예외 처리를 위한 ExceptionHadler 를 분여줄 수 있다.
    // 에 메서드 또한 예외가 발생할 때 사용자에게 응답을 보내기 위한 메서드이다
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleIllegalArgument(
            final IllegalArgumentException exception
    ) {
        ErrorDto dto = new ErrorDto();
        dto.setMessage(exception.getMessage());
        return ResponseEntity
                .badRequest()
                .body(dto);
    }

    // 추가
    /*@ExceptionHandler(UsernameExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleUsernameExists(
            final UsernameExistException exception
    ) {
        ErrorDto dto = new ErrorDto();
        dto.setMessage(exception.getMessage());
        return dto;
    }*/


    @ExceptionHandler(Status400Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handle400(
            final Status400Exception exception
    ) {
        ErrorDto dto = new ErrorDto();
        dto.setMessage(exception.getMessage());
        return dto;
    }

}

