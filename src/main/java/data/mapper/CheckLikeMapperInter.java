package data.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface CheckLikeMapperInter {
    //화이탱~~~~
    //힘이들땐 하늘을봐

    @Select("select count(*) from likes where rev_id = #{rev_id}")
    public int getCountLike(int rev_id);

//    @Insert("insert into likes (mem_id, rev_id) values (#{mem_id}, #{rev_id})")
//    public void insertLike(int mem_id, int rev_id);
    @Update("update likes SET count = count +1 where rev_id = #{rev_id}")
    public void insertLike(int rev_id);

    @Select("select `like` FROM review WHERE review_id = #{review_id}")
    int getLikeCount(int rev_id);

    @Delete("delete from likes where like_id=#{like_id}")
    public void deleteLike(int like_id);
}
