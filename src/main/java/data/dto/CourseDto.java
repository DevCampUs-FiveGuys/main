package data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Alias("CourseDto")
@Builder
@AllArgsConstructor
public class CourseDto {
    private int courseId;
    private String num;
    private String name;
    private String start_day;
    private String end_day;
}
