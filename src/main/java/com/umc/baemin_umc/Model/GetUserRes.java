package com.umc.baemin_umc.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {
    private int userIdx;
    private String id;
    private String phoneNumber;
    private String nickName;
    private String grade;
    private String address;
    private String status;
    private String createdAt;
    private String email;
    private String profile_img;
}
