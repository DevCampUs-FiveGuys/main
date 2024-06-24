package data.mapper;

import data.dto.CourseDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapperInter {


  //중복처리한 과정명을 course db에서 불러오기
    @Select("select distinct name from course")
    public List<CourseDto> getAllCourseList();

  //과정명 선택시 과정명에 해당하는 기수명을 course db에서 불러오기
    @Select("select num from course where name=#{name}")
    public List<String> getNumOfCourse(String name);

    @Insert("insert into course (name,num,start_day,end_day) VALUES (#{name},#{num}),#{start_day},#{end_day})")
    public void insertCourse(CourseDto courseDto);
}
