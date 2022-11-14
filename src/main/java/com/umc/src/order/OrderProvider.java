package com.umc.src.order;

import com.umc.config.BaseException;
import com.umc.src.order.Model.GetUserRes;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderDao olderDao;

    // 해당 유저 주문상세 조회
    public GetUserRes getUserOlder(int userIdx) throws BaseException {

            GetUserRes getUserRes = olderDao.selectUserOlder(userIdx);
            return getUserRes;

    }
}
