package com.umc.src.store.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetStoreMenuRes {
    private String menu;
    private String price;
    private String menu_img;
}
