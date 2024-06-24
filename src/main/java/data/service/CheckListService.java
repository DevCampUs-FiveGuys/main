package data.service;

import data.mapper.CheckLikeMapperInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckListService {

    @Autowired
    private CheckLikeMapperInter checkLikeMapperInter;

    public void insertLike(int rev_id) {
        checkLikeMapperInter.insertLike(rev_id);
    }


    public void deleteLike(int like_id) {
        checkLikeMapperInter.deleteLike(like_id);
    }

}
