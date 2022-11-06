package com.umc.src.post;

import com.umc.config.BaseException;
import com.umc.src.post.Model.PostPostReq;
import com.umc.src.post.Model.PostPostRes;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.umc.config.BaseResponseStatus.CREATE_FAIL_POST;

@Service
@RequiredArgsConstructor
public class PostService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PostDao postDao;

    // 게시글 작성
    public PostPostRes createPost(PostPostReq postPostReq) throws BaseException {
        try {
            int postIdx = postDao.createPost(postPostReq);
            return new PostPostRes(postIdx);
        } catch (Exception exception) {
            throw new BaseException(CREATE_FAIL_POST);
        }
    }
}
