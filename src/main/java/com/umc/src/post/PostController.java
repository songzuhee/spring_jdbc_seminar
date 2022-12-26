package com.umc.src.post;

import com.umc.config.BaseException;
import com.umc.config.BaseResponse;
import com.umc.src.post.Model.GetPostRes;
import com.umc.src.post.Model.PathPostReq;
import com.umc.src.post.Model.PostPostReq;
import com.umc.src.post.Model.PostPostRes;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.umc.config.BaseResponseStatus.POST_EMPTY_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private PostProvider postProvider;
    private PostService postService;


    @Autowired
    public PostController(PostProvider postProvider, PostService postService) {

        this.postProvider = postProvider;
        this.postService = postService;

    }
    /*
     * 게시글 조회
     * [GET] /posts/:postIdx
     */
    @ApiOperation(value = "게시글 조회 API", notes = "{postIdx}에 일치하는 게시글 조회")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청 성공"),
            @ApiResponse(code = 4000, message = "서버 에러")
    })
    @ResponseBody
    @GetMapping("/{postIdx}")
    public BaseResponse<GetPostRes> getPost(@PathVariable("postIdx")int postIdx) {
        try{
            GetPostRes getPostRes = postProvider.getPost(postIdx);
            return new BaseResponse<>(getPostRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /*
     * 게시글 작성
     * [POST] /posts/
     */
    @ApiOperation(value = "게시글 작성 API", notes = "RequestBody로 들어온 내용 작성")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청 성공"),
            @ApiResponse(code = 4000, message = "서버 에러"),
            @ApiResponse(code = 4002, message = "게시글 생성을 실패하였습니다.")
    })
    @ResponseBody
    @PostMapping("/")
    public BaseResponse<PostPostRes> creatPost(@RequestBody PostPostReq postPostReq) throws BaseException {

        try {
            if (postPostReq.getContent() == null) return new BaseResponse<>(POST_EMPTY_CONTENT);

            PostPostRes postPostRes = postService.createPost(postPostReq);
            return new BaseResponse<>(postPostRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /*
     * 게시글 삭제
     * [PATCH] /posts/:postIdx/status
     */
    @ApiOperation(value = "게시글 삭제 API", notes = "{postIdx}에 일치하는 게시글 status 값 INACTIVE 변경")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청 성공"),
            @ApiResponse(code = 4000, message = "서버 에러"),
            @ApiResponse(code = 4003, message = "게시글 삭제를 실패하였습니다.")
    })
    @ResponseBody
    @PatchMapping("/{postIdx}/status")
    public BaseResponse<String> deletePost(@PathVariable("postIdx")int postIdx)
        throws BaseException {
        postService.deletePost(postIdx);
        String result = "게시글이 삭제되었습니다. ";
        return new BaseResponse<>(result);
    }

    /*
     * 게시글 수정
     * [PATCH] /posts/change/:postIdx
     */
    @ApiOperation(value = "게시글 수정 API", notes = "{postIdx}에 일치하는 게시글 수정")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청 성공"),
            @ApiResponse(code = 4000, message = "서버 에러"),
            @ApiResponse(code = 4004, message = "게시글 수정을 실패하였습니다.")
    })
    @ResponseBody
    @PatchMapping("change/{postIdx}")
    public BaseResponse<String> updatePost(@PathVariable("postIdx") int postIdx, @RequestBody PathPostReq pathPostReq) throws BaseException {

        try {
            postService.updatePost(postIdx, pathPostReq);
            String result = "게시글 수정을 완료하였습니다. ";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}

