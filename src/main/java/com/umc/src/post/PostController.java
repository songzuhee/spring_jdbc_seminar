package com.umc.src.post;

import com.umc.config.BaseException;
import com.umc.config.BaseResponse;
import com.umc.src.post.Model.GetPostRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private PostProvider postProvider;


    @Autowired
    public PostController(PostProvider postProvider) {

        this.postProvider = postProvider;

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
}
