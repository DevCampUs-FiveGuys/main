package data.mapper;

import org.apache.ibatis.annotations.Update;

public interface TeacherMapperInter {

    @Update("update member set roles=#{roles} where member_id=#{member_id}")
    public void updateTeacher(int member_id);
}
