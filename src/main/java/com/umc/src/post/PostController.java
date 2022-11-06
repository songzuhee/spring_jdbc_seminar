package com.umc.src.post;

import com.umc.config.BaseException;
import com.umc.config.BaseResponse;
import com.umc.src.post.Model.GetPostRes;
import com.umc.src.post.Model.PostPostReq;
import com.umc.src.post.Model.PostPostRes;
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
}
