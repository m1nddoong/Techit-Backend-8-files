package com.example.contents;

// multipart/form-data 를 받기 위한 연습

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class MultipartController {
    @PostMapping(
            value = "multipart",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public String multipart(
            @RequestParam("name")
            String name,
            // 파일을 받아주는 자료형을 MultipartFile
            @RequestParam("file")
            MultipartFile multipartFile
    ) throws IOException {
        log.info(multipartFile.getOriginalFilename());
        // TODO 폴더가 없을 때 만들기

        // 파일을 저장할 '경로 + 파일명' 지정 -> 위치를 나타내는 객체 역할
        Path downloadPath = Path.of("media/" + multipartFile.getOriginalFilename());
        // 저장한다.
        multipartFile.transferTo(downloadPath);

        // 저장할 파일 이름
        /*File file = new File("./media" + multipartFile.getOriginalFilename());
        try (OutputStream outputStream = new FileOutputStream(file)) {
            // byte[] 형태로 파일을 받는다.
            byte[] fileBytes = multipartFile.getBytes();
            // TODO 이미지 파일을 구성하는 byte[] 가 맞는지 확인

            System.out.println(new String(fileBytes, StandardCharsets.UTF_8));
            outputStream.write(fileBytes);
        }*/

        return "http://localhost:8080/static" + multipartFile.getOriginalFilename();
    }
}

