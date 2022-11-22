package com.umc.src.zzim.Model;

import com.umc.src.store.Model.GetStoreMenuRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetZzimRes {
    private int ZzimCount; // 총 찜 개수
    private List<GetZzimListRes> getZzimListRes;
}
