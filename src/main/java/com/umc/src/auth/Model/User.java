package com.umc.src.auth.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor

public class User {

    private int userIdx;
    private String nickName;
    private String password;
}
