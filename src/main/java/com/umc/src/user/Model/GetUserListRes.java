package com.umc.src.user.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserListRes {
    private int userIdx;
    private String nickName;
}
