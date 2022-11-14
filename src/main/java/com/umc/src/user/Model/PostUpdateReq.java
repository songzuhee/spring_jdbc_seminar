package com.umc.src.user.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostUpdateReq {

    private String phoneNumber;
    private String nickName;
    private String email;
    private String password;

}
