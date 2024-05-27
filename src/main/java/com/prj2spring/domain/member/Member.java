package com.prj2spring.domain.member;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Member {
    private Integer id;
    private String email;
    private String password;
    private String oldPassword;
    private String nickName;
    private LocalDateTime inserted;

    public String getSignupDateTime() {
        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 hh시 mm분 ss초");

        return inserted.format(formatter);
    }
}
