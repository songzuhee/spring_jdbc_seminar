package com.umc.src.post;

import com.umc.config.BaseException;
import com.umc.config.BaseResponseStatus;
import com.umc.src.post.Model.GetPostRes;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.umc.config.BaseResponseStatus.DATABASE_ERROR;
@Service
@RequiredArgsConstructor
public class PostProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PostDao postDao;

    // 게시글 조회

    public GetPostRes getPost(int postIdx) throws BaseException {
        try {
            GetPostRes getPostRes = postDao.selectPost(postIdx);
            return getPostRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
