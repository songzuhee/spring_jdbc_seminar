package com.umc.src.Point;

import com.umc.config.BaseException;
import com.umc.src.Point.Model.GetPointRes;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.umc.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class PointProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PointDao pointDao;

    // 해당 유저의 포인트 조회
    public List<GetPointRes> getUserPointList(int userIdx) throws BaseException {
        try {
            List<GetPointRes> getPointListRes = pointDao.selectPointList(userIdx);
            return getPointListRes;
        } catch(Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
