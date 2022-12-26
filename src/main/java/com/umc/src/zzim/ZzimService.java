package com.umc.src.zzim;

import com.umc.config.BaseException;
import com.umc.config.BaseResponse;
import com.umc.src.zzim.Model.PostZzimRes;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.umc.config.BaseResponseStatus.*;

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
            throw new BaseException(FAILED_CREATE_ZZIM);
        }
    }

    // 찜 취소
    public void inactiveZzim(int userIdx, int storeIdx) throws BaseException {
        try {
            int result = zzimDao.updateZzimStatus(userIdx, storeIdx);
            if (result == 0) {
                throw new BaseException(DELETE_FAIL_ZZIM);
            }
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
