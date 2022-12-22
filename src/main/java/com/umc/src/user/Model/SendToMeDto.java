package com.umc.src.user.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SendToMeDto {
    private String nickName;
    private String email;
}
