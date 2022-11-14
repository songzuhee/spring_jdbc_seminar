package com.umc.src.order;

import com.umc.config.BaseException;
import com.umc.src.order.Model.PostUserReq;
import com.umc.src.order.Model.PostUserRes;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.umc.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class OrderService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderDao orderDao;
    private final OrderProvider orderProvider;

    // 주문하기
    @Transactional
    public PostUserRes createOrder(int userIdx, PostUserReq postUserReq) throws BaseException {

            int orderIdx = orderDao.createOrder(userIdx, postUserReq);
            return new PostUserRes(orderIdx);

    }
}
