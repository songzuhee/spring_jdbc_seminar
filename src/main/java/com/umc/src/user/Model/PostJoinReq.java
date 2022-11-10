package com.umc.src.user.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostJoinReq {
    private String id;
    private String password;
    private String phoneNumber;
    private String nickName;
    private String email;
    private String checkpwd;
}
