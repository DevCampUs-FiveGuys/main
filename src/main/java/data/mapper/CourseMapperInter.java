package data.mapper;

import data.dto.CourseDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapperInter {

    @Select("select distinct name from course")
    public List<CourseDto> getAllCourseList();

    @Select("select num from course where name=#{name}")
    public List<String> getNumOfCourse(String name);

    @Insert("insert into course (name,num,start_day,end_day) VALUES (#{name},#{num}),#{start_day},#{end_day})")
    public void insertCourse(CourseDto courseDto);

}
