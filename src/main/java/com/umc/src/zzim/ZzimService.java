package com.umc.src.zzim;

import com.umc.config.BaseException;
import com.umc.config.BaseResponse;
import com.umc.src.zzim.Model.PostZzimRes;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.umc.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class ZzimService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ZzimDao zzimDao;

    // 찜 생성
    public PostZzimRes createZzim(int userIdx, int storeIdx) throws BaseException {
        try {
            int zzimIdx = zzimDao.createZzim(userIdx, storeIdx);
            return new PostZzimRes(zzimIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
