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
    private String num;
    private String name;
    private Timestamp start_day;
    private Timestamp end_day;
}
