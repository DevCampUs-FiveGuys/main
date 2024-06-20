package data.service;

import data.mapper.CheckLikeMapperInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckListService {

    @Autowired
    private CheckLikeMapperInter checkLikeMapperInter;

    public int ShowCountLike(int rev_id){
        return checkLikeMapperInter.ShowCountLike(rev_id);
    }

    public void insertLike(int mem_id, int rev_id){
        checkLikeMapperInter.insertLike(mem_id, rev_id);
    }

    public void deleteLike(int like_id) {
         checkLikeMapperInter.deleteLike(like_id);
    }

}
