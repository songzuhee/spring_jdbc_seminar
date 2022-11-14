package com.umc.src.order;

import com.umc.config.BaseException;
import com.umc.config.BaseResponse;
import com.umc.src.order.Model.GetUserRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/olders")
public class OrderController {

    private OrderService olderService;

    private OrderProvider olderProvider;

    @Autowired
    public OrderController(OrderProvider olderProvider, OrderService olderService) {
        this.olderProvider = olderProvider;
        this.olderService = olderService;
    }

    /*
    해당 유저 주문 상세 조회
     */
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<GetUserRes> getUserOlder(@PathVariable("userIdx") int userIdx) {
        try {
            GetUserRes getUserRes = olderProvider.getUserOlder(userIdx);
            return new BaseResponse<>(getUserRes);
        } catch(BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
