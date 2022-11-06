package com.umc.src.post.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetPostRes {

    private int postIdx;
    private String nickName;
    private String imgUrl;
    private String content;
}
