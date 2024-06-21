package data.mapper;

import data.dto.CourseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapperInter {

    @Select("select distinct name from course")
    public List<CourseDto> getAllCourseList();

    @Select("select num from course where name=#{name}")
    public List<String> getNumOfCourse(String name);

}
