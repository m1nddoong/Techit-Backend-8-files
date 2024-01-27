package com.example.contents.exceptions;


// 사용자 이름이 중복일 떄 발생하는 예외
public class UsernameExistException extends Status400Exception { // 일종의 예외이다 라는 표시 (상속받기)
    public UsernameExistException() { // 생성자 만들어주고
        super("username exist"); // 상속받은 부모클래스의 생성자를 사용

    }
}
