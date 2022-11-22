package com.umc.src.zzim;

import com.umc.config.BaseException;
import com.umc.config.BaseResponseStatus;
import com.umc.src.zzim.Model.GetZzimRes;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.umc.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class ZzimProvider {

    final Logger  logger = LoggerFactory.getLogger(this.getClass());

    private final ZzimDao zzimDao;

    // 찜 조회
    public List<GetZzimRes> UserZzimList(int userIdx) throws BaseException {
        try {
            List<GetZzimRes> getZzimListRes = zzimDao.selectZzimList(userIdx);
            return getZzimListRes;
        } catch(Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
