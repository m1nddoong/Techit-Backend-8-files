package com.example.contents.service;

import com.example.contents.dto.UserDto;
import com.example.contents.entity.User;
import com.example.contents.repository.UserRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    // CREATE USER : 회원 가입
    public UserDto create(UserDto dto) {
        User newUser = new User(
                dto.getUsername(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getBio()
        );
        newUser = repository.save(newUser);
        return UserDto.fromEntity(newUser);
    }

    // READ USER BY USERNAME : 회원 정보 조회
    public UserDto readByUsername(String username) {
        /* Optional<User> optionalUser
                = repository.findByUsername(username);
        if (optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return UserDto.fromEntity(optionalUser.get()); */

        // 축약형
        return repository.findByUsername(username)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    // UPDATE USER AVATAR : 회원 프로필 아이콘 업데이트
    public UserDto updateUserAvatar(Long id, MultipartFile image) {
        // 1. 유저의 존재 확인
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // 2. 파일을 어디에 업로드 할건지 결정
        // media/{id}/profile.{확장자}
        // ㄴ 2-1. (없다면) 폴더를 만들어야 한다. (media/{id}) 만들었다!
        String profileDir = String.format("media/%d/", id);
        log.info(profileDir);
        try {
            // 주어진 Path를 기준으로, 없는 모든 디렉토리를 생성하는 메서드
            Files.createDirectories(Path.of(profileDir));
        } catch (IOException e) {
            // 폴더를 만드는데 실패하면 기록을 하고 사용자에게 알림
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

        }
        // ㄴ 2-2. 실제 파일 이름을 경로와 확장자를 포함하여 만들기 (profile.png 만들기)
        String originalFilename = image.getOriginalFilename(); // 원본 이미지 가져오기
        // "whale.png" -> { "whale", "png" }
        String[] filenameSplit = originalFilename.split("\\."); // 확장자 분리하기 (. 을 기준 분리 <- 정규표현식)
        // "blue.whale.png" -> { "blue", "whale", "png" }
        String extension = filenameSplit[filenameSplit.length - 1];
        String profileFilename = "profile" + extension; // 파일명 만들었다!
        log.info(profileFilename);

        String profilePath = profileDir + profileFilename;
        log.info(profilePath);


        // 3. 실제로 해당 위치에 파일을 저장
        try {
            image.transferTo(Path.of(profilePath));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // 4. User에 아바타 위치를 저장
        // http://localhost:8080/static/{id}/profile.{확장자}
        String requestPath = String.format("/static/%d/%s", id, profileFilename);
        log.info(requestPath);
        User target = optionalUser.get();
        target.setAvatar(requestPath); // 아바타에 나의 요청 경로 넣어주기

        // 5. 응답하기
        return UserDto.fromEntity(repository.save(target));


    }

}
