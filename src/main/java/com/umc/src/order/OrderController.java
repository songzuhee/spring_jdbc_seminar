package com.umc.src.order;

import com.umc.config.BaseException;
import com.umc.config.BaseResponse;
import com.umc.src.order.Model.GetUserListRes;
import com.umc.src.order.Model.GetUserRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    private OrderProvider orderProvider;

    @Autowired
    public OrderController(OrderProvider orderProvider, OrderService orderService) {
        this.orderProvider = orderProvider;
        this.orderService = orderService;
    }

    /*
    해당 유저 주문 상세 조회
     */
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<GetUserRes> getUserOlder(@PathVariable("userIdx") int userIdx) {
        try {
            GetUserRes getUserRes = orderProvider.getUserOlder(userIdx);
            return new BaseResponse<>(getUserRes);
        } catch(BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /*
    해당 유저의 전체 주문 내역
     */

    @ResponseBody
    @GetMapping("/{userIdx}/List")
    public BaseResponse<List<GetUserListRes>> getUserOrderList(@PathVariable("userIdx") int userIdx) {
        try {
            List<GetUserListRes> getUserListRes = orderProvider.UserOrderList(userIdx);
            return new BaseResponse<>(getUserListRes);
        } catch(BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
