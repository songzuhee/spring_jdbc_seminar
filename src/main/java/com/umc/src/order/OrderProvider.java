package com.umc.src.order;

import com.umc.config.BaseException;
import com.umc.config.BaseResponseStatus;
import com.umc.src.order.Model.GetUserListRes;
import com.umc.src.order.Model.GetUserRes;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderDao orderDao;

    // 해당 유저 주문상세 조회
    public GetUserRes getUserOlder(int userIdx) throws BaseException {

            GetUserRes getUserRes = orderDao.selectUserOlder(userIdx);
            return getUserRes;

    }

    // 해당 유저 주문 전체 조회
    public List<GetUserListRes> UserOrderList(int userIdx) throws BaseException {

            List<GetUserListRes> getUserListRes = orderDao.selectUserOrderList(userIdx);
            return getUserListRes;

    }
}
