package com.umc.src.post;

import com.umc.config.BaseException;
import com.umc.src.post.Model.PathPostReq;
import com.umc.src.post.Model.PostPostReq;
import com.umc.src.post.Model.PostPostRes;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.umc.config.BaseResponseStatus.*;

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

    // 게시글 삭제
    public void deletePost(int postIdx) throws BaseException {

        try {
            int result = postDao.deletePost(postIdx);
            if (result == 0) {
                throw new BaseException(DELETE_FAIL_POST);
            }
        } catch (Exception exception) {
                throw new BaseException(DATABASE_ERROR);
            }
    }

    // 게시글 수정
    public void updatePost(int postIdx, PathPostReq pathPostReq) throws BaseException {

        int result = 0;

        try {
            if(pathPostReq.getContent() != null) {
                result = postDao.updatePost(postIdx, pathPostReq);
            }

            if (result == 0) throw new BaseException(UPDATE_FAIL_POST);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
