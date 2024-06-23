package data.service;

import data.mapper.CheckLikeMapperInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckListService {

    @Autowired
    private CheckLikeMapperInter checkLikeMapperInter;

    public int ShowCountLike(int rev_id){
        return checkLikeMapperInter.getCountLike(rev_id);
    }

    public void insertLike(int rev_id){
        checkLikeMapperInter.insertLike(rev_id);
    }

    public int getLikeCount(int rev_id){
        checkLikeMapperInter.getLikeCount(rev_id);
        return rev_id;
    }

    public void deleteLike(int like_id) {
         checkLikeMapperInter.deleteLike(like_id);
    }

}
