package data.mapper;

import data.dto.CourseDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapperInter {

    //중복처리한 과정명을 course db에서 불러오기
    @Select("select distinct name from course")
    public List<CourseDto> getAllCourseList();

    //과정명 선택시 과정명에 해당하는 기수명을 course db에서 불러오기
    @Select("select num from course where name=#{name}")
    public List<String> getNumOfCourse(String name);

    // 새로운 훈련 추가
    @Insert("insert into course (name,num, start_day, end_day) VALUES (#{name},#{num}, #{start_day}, #{end_day})")
    public void insertCourse(CourseDto courseDto);

    // 과정명 정보 전체 불러오기
    @Select("select * from course")
    public List<CourseDto> getCourseInfo();
    
    // 훈련 삭제
    @Delete("delete from course where name = #{name} and num = #{num}")
    public void deleteCourse(String name, String num);
}
