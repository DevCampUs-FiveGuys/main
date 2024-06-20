package data.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface CheckLikeMapperInter {
    //화이탱~~~~
    //힘이들땐 하늘을봐

    @Select("select count(*) from checklike where rev_id = #{rev_id}")
    public int getCountLike(int rev_id);

    @Insert("insert into checklike (mem_id, rev_id) values (#{mem_id}, #{rev_id})")
    public void insertLike(int mem_id, int rev_id);

    @Delete("delete from checklike where like_id=#{like_id}")
    public void deleteLike(int like_id);
}
