package com.umc.src.order;

import com.umc.config.BaseException;
import com.umc.config.BaseResponse;
import com.umc.src.order.Model.GetUserListRes;
import com.umc.src.order.Model.GetUserRes;
import com.umc.src.order.Model.PostUserReq;
import com.umc.src.order.Model.PostUserRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.umc.config.BaseResponseStatus.*;

@Api
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
     * 해당 유저 주문 상세 조회
     * [GET] /users/:userIdx/:orderIdx
     */
    @ApiOperation(value = "주문 상세조회 API", notes ="PathVariable로 userIdx, orerIdx 받아와서 주문 상세조회")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청 성공"),
            @ApiResponse(code = 4000, message = "서버 에러")
    })
    @ResponseBody
    @GetMapping("/{userIdx}/{orderIdx}")
    public BaseResponse<GetUserRes> getUserOlder(@PathVariable("userIdx") int userIdx, @PathVariable("orderIdx") int orderIdx) {
        try {
            GetUserRes getUserRes = orderProvider.getUserOlder(userIdx, orderIdx);
            return new BaseResponse<>(getUserRes);
        } catch(BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /*
     * 해당 유저의 전체 주문 내역
     * [GET] /users/:userIdx
     */
    @ApiOperation(value = "주문 전체 API", notes ="PathVariable로 userIdx 받아와서 주문 전체조회")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청 성공"),
            @ApiResponse(code = 4000, message = "서버 에러")
    })
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetUserListRes>> getUserOrderList(@PathVariable("userIdx") int userIdx) {
        try {
            List<GetUserListRes> getUserListRes = orderProvider.UserOrderList(userIdx);
            return new BaseResponse<>(getUserListRes);
        } catch(BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /*
     * 주문 생성 API
     * [POST] /users/:userIdx
     */
    @ApiOperation(value = "주문 생성 API", notes ="PathVariable로 받은 userIdx의 주문 생성")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청 성공"),
            @ApiResponse(code = 4000, message = "서버 에러"),
            @ApiResponse(code = 5007, message = "결제 방식을 선택해주세요. "),
            @ApiResponse(code = 5008, message = "금액을 입력해주세요. ")
    })
    @ResponseBody
    @PostMapping("/{userIdx}")
    public BaseResponse<PostUserReq> createOrder(@PathVariable("userIdx") int userIdx, @RequestBody PostUserReq postUserReq) throws BaseException {
        try {
            if (postUserReq.getPay_method() == null)
            {
                return new BaseResponse<>(ORDER_EMPTY_METHOD);
            }
            if (postUserReq.getTotal_price() == 0)
            {
                return new BaseResponse<>(ORDER_EMPTY_PRICE);
            }

            PostUserRes postUserRes = orderService.createOrder(userIdx, postUserReq);
            return new BaseResponse<>(postUserReq);
        }   catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }
}
